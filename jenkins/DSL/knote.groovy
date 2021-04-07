pipeline {
    agent any
    stages {
        stage('Clone') {
            steps {
                git 'https://github.com/KietChan/test-okteto-deployment'
            }

            // post {
            //     // If Maven was able to run the tests, even if some of the test
            //     // failed, record the test results and archive the jar file.
            //     success {
            //         junit '**/target/surefire-reports/TEST-*.xml'
            //         archiveArtifacts 'target/*.jar'
            //     }
            // }
        }
        stage('Build') {
            steps {
                sh 'cd app'
                sh 'npm install'
            }
        }
        stage('Build Docker') {
            steps {
                sh 'Echo "Test"'
            }
        }
    }
}
