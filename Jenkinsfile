pipeline {
  agent any
  environment {
    JAVA_HOME='/usr/lib/jvm/zulu-12-amd64/'
  }
  stages {
    stage('service_1') {
        stage('test & build') {
          steps {
            sh '''
            cd service_1
            ./mvnw clean package
            '''
          }
        }
    }
    stage('service_2') {
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