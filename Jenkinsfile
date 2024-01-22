pipeline {
    agent any

    tools {
        maven 'Maven' // or the name of the Maven version you've configured in Jenkins
        jdk 'JDK17'    // or the name of the JDK version you've configured
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/daveb8772/CMS_POC_App.git', branch: 'master'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        // Additional stages like 'Deploy' can be added here
    }
}
