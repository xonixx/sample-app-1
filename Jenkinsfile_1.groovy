pipeline {
  agent any
  tools {
    // https://stackoverflow.com/a/55244659
    // https://stackoverflow.com/a/53375151
    jdk 'jdk11'
  }
  environment {
      SSH_CREDS = credentials('apps1')
  }
  stages {
    stage('test & build') {
      steps {
        slackSend message: "Build Started - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
        sh 'java -version'
        sh 'groups'
        sh '''
            ./mvnw clean package
        '''
      }
    }
    stage('Sonarqube') {
        environment {
            scannerHome = tool 'sonarqube-scanner'
        }
        steps {
            withSonarQubeEnv('sonarqube-1') {
                sh '''${scannerHome}/bin/sonar-scanner          \
                -Dsonar.projectKey=microservice-blueprint-1     \
                -Dsonar.sources=./src                           \
                -Dsonar.java.binaries=./target/classes,./target/test-classes
                '''
            }
            timeout(time: 10, unit: 'MINUTES') {
                // this needs setting up webhook in SonarQube
                waitForQualityGate abortPipeline: true
            }
        }
    }
    stage('deploy') {
        steps {
            sh '''
                ./mvnw dockerfile:build dockerfile:push
                cp -f "$SSH_CREDS" /tmp/1.key
                bash ../deploy_k8s_1.sh microservice-blueprint-1 /tmp/1.key 8080
            '''
        }
    }
  }
    post {
        always {
            sh 'rm -f /tmp/1.key'

            // TODO email on failed only
            emailext(
                subject: '$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS!',
                body: '''$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS:
    
                         Check console output at $BUILD_URL to view the results.''',
                recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
                to: 'xonixx+j@gmail.com'
            )

            junit 'target/surefire-reports/**/*.xml'
        }
        failure {
            slackSend message: "Build FAIL - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
        }
        success {
            slackSend message: "Build SUCCESS - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
            archiveArtifacts artifacts: 'target/**/*.jar', fingerprint: true
        }
    }
}