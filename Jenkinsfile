Jenkinsfile (Declarative Pipeline)
pipeline {
    agent any

    environment {
        AWS_ACCESS_KEY_ID = credentials('AWS_ID')
        AWS_SECRET_ACCESS_KEY = credentials('AWS_KEY')
        AWS_DEFAULT_REGION = 'ap-northeast-2'
        HOME = '.'
    }

    stages {

        stage('git clone') {
            agent any

            steps {
                echo("git clone")

                git url: 'https://github.com/OPGG-HACKTHON/web-team-c-backend',
                    branch: 'master',
                    credentialsId: 'swoomi_git_credentials'
            }

            post {
                success {
                  echo 'Success Pulling Repository'
                }

                failure {
                    echo 'Fail Pulling Repository'
                }
            }
        }

        stage ("build TEST") {
            agent {
                docker {
                    image 'openjdk:11'
                }
            }

            steps {
              dir ('./') {
                  sh '''
                  ./gradlew test
                  '''
              }
            }
        }

        stage ("build JAR") {
            agent {
                docker {
                    image 'openjdk:11'
                }
            }

            steps {
              dir ('./') {
                  sh '''
                  ./gradlew bootJar
                  '''
              }
            }
        }
    }
}