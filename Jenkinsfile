pipeline {
    agent any

    tools {
        maven 'Maven' // or the name of the Maven version you've configured in Jenkins
        jdk 'JDK17'    // or the name of the JDK version you've configured
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/daveb8772/CMS_POC_App.git', branch: 'main'
            }
        }
        stage('Run Docker Compose') {
            steps {
                script {
                    // Run Docker Compose to set up the environment
                    sh 'docker-compose up -d'
                }
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

        stage('Cleanup') {
            steps {
                script {
                    // Take down the Docker Compose setup
                    sh 'docker-compose down'
                }
            }
        }
    }

    post {
        always {
            // Clean up regardless of success or failure
            sh 'docker-compose down'
        }
    }

}
