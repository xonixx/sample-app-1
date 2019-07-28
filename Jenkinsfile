pipeline {
  agent any
  environment {
  }
  tools {
    jdk 'jdk11'
  }
  stages {
    stage('service_1') {
        stages {
            stage('test & build') {
              steps {
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