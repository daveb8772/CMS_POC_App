pipeline {
    agent any
    environment {
        dockerPath = ''
    }
    tools {
        maven 'Maven' // Maven version configured in Jenkins
        jdk 'JDK17'   // JDK version configured in Jenkins
    }

    stages {
        stage ('Set Environment') {
            steps {
                script {
                    dockerPath = sh(script: 'echo $DOCKER_PATH', returnStdout: true).trim()
                    sh 'env'
                    sh 'echo $DOCKER_PATH'

                    if (dockerPath) {
                        sh "${dockerPath} --version"
                    } else {
                        error("Docker path is not set in the environment variables.")
                    }
                }
            }
        }

        stage('Add Docker Credential Path') {
            steps {
                script {
                    env.PATH = env.PATH + ':/usr/local/bin/docker-credential-osxkeychain'
                    echo "Updated PATH: ${env.PATH}"
                }
            }
        }

        stage('Checkout') {
            steps {
                git url: 'https://github.com/daveb8772/CMS_POC_App.git', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('List Target Directory') {
            steps {
                sh 'ls -l target/'
            }
        }

        stage('Run Docker Compose') {
            steps {
                script {
                    sh "${dockerPath} compose up -d"
                }
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
                    sh "${dockerPath} compose down"
                }
            }
        }
    }

    post {
        always {
            script {
                if (dockerPath) {
                    sh "${dockerPath} compose down"
                } else {
                    echo "Docker path not set. Skipping docker compose down."
                }
            }
        }
    }
}
