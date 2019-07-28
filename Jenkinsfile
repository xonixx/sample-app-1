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
}