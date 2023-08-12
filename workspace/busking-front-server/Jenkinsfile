/* groovylint-disable CompileStatic, DuplicateStringLiteral */

MSG_PREFIX = '[Busking CI/CD] '

void sendErrorMessage(String msg) {
    mattermostSend(message: MSG_PREFIX + msg, color: '#FF0000')
}

void sendSuccessMessage(String msg) {
    mattermostSend(message: MSG_PREFIX + msg, color: '#8BC34A')
}

pipeline {
    agent any

    stages {
        stage('Build Front-end Project') {
            agent {
                docker {
                    image 'node:18'
                }
            }

            steps {
                mattermostSend(message: '[Busking CI/CD] Start to build the front-end project ...')

                dir('workspace/frontend') {
                    sh 'npm install'
                    sh 'rm -rf build'
                    sh 'CI=false npm run build'
                    sh 'tar czf frontend.build.tar.gz build'
                    archiveArtifacts(artifacts: 'frontend.build.tar.gz', fingerprint: true)
                    stash(name: 'frontend.build', includes: 'frontend.build.tar.gz')
                }
            }

            post {
                failure {
                    sendErrorMessage('Building the front-end project was failed.')
                }

                success {
                    sendSuccessMessage('The front-end project was successfully built.')
                }
            }
        }

        stage('Build Back-end Project') {
            agent {
                docker {
                    image 'gradle:8.2.1-jdk17-jammy'
                }
            }

            steps {
                mattermostSend(message: '[Busking CI/CD] Start to build back-end project ...')

                dir('workspace/busking-front-server') {
                    unstash(name: 'frontend.build')
                    sh 'tar zxf frontend.build.tar.gz && rm frontend.build.tar.gz'
                    sh 'rm -rf src/main/resources/static'
                    sh 'mv build src/main/resources/static'
                    sh 'rm -rf src/test'

                    sh 'chmod +x ./gradlew && ./gradlew build'
                    sh 'rm build/libs/*plain*'
                    sh 'rm -f app.jar && mv build/libs/* app.jar'
                    archiveArtifacts(artifacts: 'app.jar', fingerprint: true)
                    stash(name: 'backend.build', includes: 'app.jar')
                }
            }

            post {
                failure {
                    sendErrorMessage('Building the back-end project was failed.')
                }

                success {
                    sendSuccessMessage('The back-end project was successfully built.')
                }
            }
        }

        stage('Build Container Image') {
            steps {
                sh 'rm -rf Dockerfile && mkdir Dockerfile'

                dir('Dockerfile') {
                    unstash(name: 'backend.build')

                    writeFile(file: 'Dockerfile', text: '''
                        FROM openjdk:17
                        COPY app.jar /app.jar
                        EXPOSE 443
                        CMD [ "java", "-Xms512m", "-Xmx1024m", "-jar", "app.jar" ]
                    '''.stripIndent())

                    sh 'docker build -t me0s/busking:latest .'
                    sh 'docker image prune'
                }
            }

            post {
                failure {
                    sendErrorMessage('Failure on building a container image.')
                }
            }
        }

        stage('Deploy Container Image') {
            steps {
                sh 'echo $DOCKERHUB_TOKEN | docker login -u me0s --password-stdin'
                sh 'docker push me0s/busking:latest'
            }

            post {
                always {
                    sh 'docker logout'
                }

                failure {
                    sendErrorMessage('Failure on deploying a container image to Docker Hub.')
                }

                success {
                    /* groovylint-disable-next-line LineLength */
                    sendSuccessMessage('The container image was deployed. Visit https://hub.docker.com/repository/docker/me0s/busking !')
                }
            }
        }
    }
}
