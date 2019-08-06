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
    stage('service_1') {
        stages {
            stage('test & build') {
              steps {
                slackSend message: "Build Started - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
                sh 'java -version'
                sh 'groups'
                sh '''
                    cd service_1
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
                        sh '''${scannerHome}/bin/sonar-scanner \
                        -Dsonar.projectKey=sample-app-1        \
                        -Dsonar.sources=./service_1/src        \
                        -Dsonar.java.binaries=./service_1/target/classes,./service_1/target/test-classes
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
                        cp -f "$SSH_CREDS" /tmp/1.key
                        cd service_1
                        ./mvnw dockerfile:build dockerfile:push
                        bash ../deploy_k8s.sh service-1 /tmp/1.key 34701
                    '''
                }
            }
        }
    }
    stage('service_2') {
        stages {
            stage('test & build') {
              steps {
                sh '''
                    cd service_2
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
                        sh '''${scannerHome}/bin/sonar-scanner \
                        -Dsonar.projectKey=sample-app-1        \
                        -Dsonar.sources=./service_2/src        \
                        -Dsonar.java.binaries=./service_2/target/classes,./service_2/target/test-classes
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
                        cp -f "$SSH_CREDS" /tmp/1.key
                        cd service_2
                        ./mvnw dockerfile:build dockerfile:push
                        bash ../deploy_k8s.sh service-2 /tmp/1.key 34702
                    '''
              }
            }
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

            junit 'service_1/target/surefire-reports/**/*.xml'
            junit 'service_2/target/surefire-reports/**/*.xml'
        }
        failure {
            slackSend message: "Build FAIL - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
        }
        success {
            slackSend message: "Build SUCCESS - ${env.JOB_NAME} ${env.BUILD_NUMBER} (<${env.BUILD_URL}|Open>)"
            archiveArtifacts artifacts: 'service_1/target/**/*.jar', fingerprint: true
            archiveArtifacts artifacts: 'service_2/target/**/*.jar', fingerprint: true
        }
    }
}