pipeline {
    agent any
    environment {
        dockerPath = ''
    }
    parameters {
        booleanParam(name: 'SKIP_TESTS', defaultValue: true, description: 'Skip running tests during build')
    }

    tools {
        maven 'Maven' // Maven version configured in Jenkins
        jdk 'JDK17'   // JDK version configured in Jenkins
    }

    stages {
        stage('Setup and Checkout') {
            steps {
                script {
                    dockerPath = sh(script: 'echo $DOCKER_PATH', returnStdout: true).trim()
                    if (dockerPath) {
                        sh "${dockerPath} --version"
                    } else {
                        error("Docker path is not set in the environment variables.")
                    }
                }
                git url: 'https://github.com/daveb8772/CMS_POC_App.git', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                script {
                    if (params.SKIP_TESTS) {
                        sh 'mvn clean package -DskipTests'
                    } else {
                        sh 'mvn clean package'
                    }
                }
            }
        }

        stage('Run Docker Compose') {
            when {
                expression { !params.SKIP_TESTS }
            }
            steps {
                script {
                    sh "${dockerPath} compose up -d"
                }
            }
        }

        stage('Test') {
            when {
                expression { !params.SKIP_TESTS }
            }
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
