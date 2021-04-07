pipeline {
    agent any
    stages {
        stage('Clone') {
            steps {
                git 'https://github.com/KietChan/test-okteto-deployment'
            }
        }
        stage('NodeJS Build') {
            steps {
                sh 'npm install app/'
            }
        }
        stage('Docker Build') {
            steps {
                sh "docker build --platform linux/amd64 -f app/Dockerfile -t lazyk9/knote:latest -t lazyk9/knote:0.0.$BUILD_NUMBER ./app"
            }
        }
        stage('Docker Push') {
            steps {
                sh "docker image push lazyk9/knote:latest"
                sh "docker image push lazyk9/knote:0.0.$BUILD_NUMBER"
            }
        }
        stage('Deploy') {
            steps {
                sh "okteto pipeline deploy --repository=https://github.com/KietChan/test-okteto-deployment"
            }
        }
    }
}
