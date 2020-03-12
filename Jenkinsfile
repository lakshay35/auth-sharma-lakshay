pipeline {
    environment {
        DOCKER_IMAGE = "lakshay35/auth-service"
        DOCKER_CREDS = "dockerhub"
        DO_ACCESS_TOKEN = credentials('doctl-token')
        DO_CLUSTER = credentials('doctl-cluster')
    }

    agent any


    stages {
       stage('CLone Git Repository') {
            steps {
                script {
                    try {
                        notifyBuild('STARTED')
                        git credentialsId: 'github-creds', url: 'https://github.com/lakshay35/auth-sharma-lakshay', branch: 'master'
                    } catch (e) {
                        error()
                    }
                }
            }
        }

        stage('Docker Image Build & Publish') {
            steps {
                script {
                    try {
                        def image = docker.build(DOCKER_IMAGE, '-f auth-service.Dockerfile .')
                        docker.withRegistry('', DOCKER_CREDS) {
                            image.push("${env.BUILD_NUMBER}")
                            image.push("latest")
                        }

                        sh 'docker system prune --all'
                    } catch (e) {
                        error()
                    }
                }
            }
        }

        stage('k8s Release') {
            steps {
                script {
                    try {

                        echo 'Logging into doctl'
                        sh 'doctl auth init -t ${DO_ACCESS_TOKEN}'
                        echo 'Configuring k8s cluster'
                        sh 'doctl kubernetes cluster kubeconfig save ${DO_CLUSTER}'

                        echo 'Deleting app from kuberenetes cluster'
                        sh "helm delete auth-service"

                        echo 'Releasing app to kuberenetes cluster'
                        sh "helm install auth-service ./auth-service"
                    } catch (e) {
                        error()
                    }
                }

            }
        }

        stage('Regression API Test') {
            steps {
                script {
                    try {
                        sh 'sleep 30'
                        echo 'running regression tests on api'
                        def image = docker.build('pm-reg-tests', '-f regression.Dockerfile .')
                        sh 'docker system prune --all'
                    } catch (e) {
                        error()
                    }
                }
            }
        }
    }
    post {
        success {
            notifyBuild()
        }
        failure {
            notifyBuild('FAILED')
        }
        fixed {
            notifyBuild('FIXED')
        }
    }
}

def notifyBuild(String buildStatus = 'SUCCESSFUL') {
  // build status of null means successful
  buildStatus =  buildStatus ?: 'SUCCESSFUL'

  // Default values
    def colorName = 'RED'
    def colorCode = '#FF0000'
    def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"



  // Override default values based on build status
  if (buildStatus == 'FIXED') {
    color = 'YELLOW'
    colorCode = '#FFFF00'
  } else if (buildStatus == 'SUCCESSFUL') {
    color = 'GREEN'
    colorCode = '#00FF00'
  } else if (buildStatus == 'STARTED') {
    color = 'GREY'
    colorCode = '#808080'
  } else {
    subject = "${buildStatus}: Job '${STAGE_NAME} [${env.BUILD_NUMBER}]'"
    color = 'RED'
    colorCode = '#FF0000'
  }

   def summary = "${subject} (${env.BUILD_URL})"

  // Send notifications
  slackSend (color: colorCode, message: summary, channel: 'auth-sharma-lakshay')
}