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
                sh 'npm install app/'
            }
        }
        stage('Docker') {
            steps {
                sh "docker build --platform linux/amd64 -f app/Dockerfile -t lazyk9/knote:latest -t lazyk9/knote:0.0.$BUILD_NUMBER ./app"
                sh "docker image push lazyk9/knote:latest"
                sh "docker image push lazyk9/knote:0.0.$BUILD_NUMBER"
            }
        }
        stage('Git') {
            steps {
                sh 'sed "s/__build_version__/0.0.$BUILD_NUMBER/g" k8s-dev/knote.yaml > k8s/knote.yaml'
                sh 'sed "s/__build_version__/0.0.$BUILD_NUMBER/g" k8s-dev/minio.yaml > k8s/minio.yaml'
                sh 'sed "s/__build_version__/0.0.$BUILD_NUMBER/g" k8s-dev/mongo.yaml > k8s/mongo.yaml'
                sh 'git add k8s/'
                sh "git commit -m 'Update K8S Build to Version 0.0.$BUILD_NUMBER'"
                sh 'git push --set-upstream origin master'
            }
        }
        stage('Deploy') {
            steps {
                sh "okteto pipeline deploy --repository=https://github.com/KietChan/test-okteto-deployment"
            }
        }
    }
}
