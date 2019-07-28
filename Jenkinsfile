pipeline {
  agent any
  tools {
    jdk 'jdk11' // https://stackoverflow.com/a/55244659
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