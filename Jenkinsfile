pipeline {
  agent any
  tools {
  // https://stackoverflow.com/a/55244659
  // https://stackoverflow.com/a/53375151
    jdk 'jdk11'
  }
  stages {
    stage('service_1') {
        stages {
            stage('test & build') {
              steps {
                sh 'java -version'
                sh '''
                cd service_1
                ./mvnw clean package
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
        }
    }
  }
    post {
        always {
            junit 'service_1/target/surefire-reports/**/*.xml'
            junit 'service_2/target/surefire-reports/**/*.xml'

            // TODO email on failed only
            emailext(
            subject: '$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS!',
            body: '''$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS:

                     Check console output at $BUILD_URL to view the results.''',
            recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
            to: 'xonixx+j@gmail.com'
            )
        }
        success {
            archiveArtifacts artifacts: 'service_1/target/**/*.jar', fingerprint: true
            archiveArtifacts artifacts: 'service_2/target/**/*.jar', fingerprint: true
        }
    }
}