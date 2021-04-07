pipeline {
    agent any
    stages {
        stage('Clone') {
            steps {
                git 'https://github.com/KietChan/test-okteto-deployment'
            }
        }
        stage('Build') {
            steps {
                sh 'cd app'
                sh 'npm install'
            }
        }
        stage('Docker Processing') {
            steps {
                sh "cd app"
                sh "docker build -f dockerfile -t lazyk9/knote:v0.0.$BUILD_NUMBER ."
                sh "docker image push lazyk9/knote:v0.0.$BUILD_NUMBER"
            }
        }
    }
}
