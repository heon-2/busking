pipeline {
    agent any

    stages {
        stage('Build frontend project') {
            agent {
                docker {
                    image 'node:18'
                }
            }

            steps {
                dir('workspace/frontend') {
                    sh 'npm install'
                    sh 'rm -rf build'
                    sh 'CI=false npm run build'
                    sh 'tar czf frontend.build.tar.gz build'
                    archiveArtifacts(artifacts: 'frontend.build.tar.gz', fingerprint: true)
                    stash(name: 'frontend.build', includes: 'frontend.build.tar.gz')
                }
            }
        }

        stage('Build backend project') {
            agent {
                docker {
                    image 'gradle:8.2.1-jdk17-jammy'
                }
            }

            steps {
                dir('workspace/busking-front-server') {
                    unstash(name: 'frontend.build')
                    sh 'tar zxf frontend.build.tar.gz && rm frontend.build.tar.gz'
                    sh 'mv build src/main/resources/static'
                    sh 'rm -rf src/test'

                    sh 'chmod +x ./gradlew && ./gradlew build'
                    sh 'rm ./build/libs/*plain*'
                    archiveArtifacts(artifacts: './build/libs/*.jar', fingerprint: true)
                    stash(name: 'backend.build', includes: './build/libs/*.jar')
                }
            }
        }
    }
}
