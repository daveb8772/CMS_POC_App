pipeline {
    agent any
    environment {
        //DOCKER_PATH = '~/.docker/bin'
        //DOCKER = 'docker'
    }
    tools {
        maven 'Maven' // or the name of the Maven version you've configured in Jenkins
        jdk 'JDK17'    // or the name of the JDK version you've configured

    }

    stages {
        stage ('environment')
        {
            steps{
                sh 'env'
                sh 'echo $PATH'
                // Reading the Docker path from the environment variable
                def dockerPath = sh(script: 'echo $DOCKER_PATH', returnStdout: true).trim()

                // Check if Docker path is set
                if (dockerPath) {
                    // Use the Docker path in your command
                    sh "${dockerPath} --version"
                } else {
                    error("Docker path is not set in the environment variables.")
                    error("e.g. set docker path by adding to .bashrc: export DOCKER_PATH=/usr/bin/docker ")
                }
            }
        }
        stage('Checkout') {
            steps {
                git url: 'https://github.com/daveb8772/CMS_POC_App.git', branch: 'main'
            }
        }
        stage('Run Docker Compose') {
            steps {
                script {
                    // Run Docker Compose to set up the environment
                    sh "${dockerPath} compose up -d"
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
                    sh "${dockerPath} compose down"
                }
            }
        }
    }

    post {
        always {
            // Clean up regardless of success or failure
            sh "${dockerPath} compose down"
        }
    }

}
