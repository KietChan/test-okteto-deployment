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
                echo "Test"
                sh "docker build -f app/Dockerfile -t lazyk9/knote:v0.0.$BUILD_NUMBER ."
            }
        }
        stage('Docker Push') {
            steps {
                sh "docker image push lazyk9/knote:v0.0.$BUILD_NUMBER"
            }
        }
    }
}
