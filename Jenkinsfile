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
                sh 'java -version'
                sh 'groups'
                sh '''
                    cd service_1
                    ./mvnw clean package
                '''
              }
            }
            stage('deploy') {
                steps {
                    sh '''
                        cp -f "$SSH_CREDS" /tmp/1.key
                    '''
//                 sh '''
//                     bash ./deploy.sh service_1 /tmp/1.key
//                 '''
                    sh '''
                        cd service_1
                        ./mvnw dockerfile:build dockerfile:push
                        bash ../deploy_k8s.sh service-1 /tmp/1.key
                    '''
                }
            }
        }
    }
    stage('service_2') {
        stages {
            stage('test & build') {
              steps {
                sh ''
//                 sh '''
//                     cd service_2
//                     ./mvnw clean package
//                 '''
              }
            }
//             stage('deploy') {
//               steps {
//                 sh '''
//                     cp -f "$SSH_CREDS" /tmp/1.key
//                     bash ./deploy.sh service_2 /tmp/1.key
//                 '''
//               }
//             }
        }
    }
  }
    post {
        always {
            sh 'rm -f /tmp/1.key'

            // TODO Slack integration
            // TODO Manual step - confirm to run a step

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
        success {
            archiveArtifacts artifacts: 'service_1/target/**/*.jar', fingerprint: true
            archiveArtifacts artifacts: 'service_2/target/**/*.jar', fingerprint: true
        }
    }
}