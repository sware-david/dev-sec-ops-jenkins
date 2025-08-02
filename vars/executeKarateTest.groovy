def call(String env = "dev") {
    env = env.toLowerCase()
    def ENVIRONMENT = env
    environmentValidate(ENVIRONMENT)

    def JAVA_ARGS = ""
    def TAGS = params.TAGS
    if (TAGS == null || TAGS.isEmpty()) {
        TAGS = "@smoke"
    }

    pipeline {
        agent { label 'local_worker' }
        stages {
            stage('checkin tools') {
                steps {
                    script {
                        JAVA_ARGS += secretLoadCredentials(env)
                        JAVA_ARGS += secretLoadJiraXrayCredentials()
                        String[] listSecrets = JAVA_ARGS.trim().split(" {0,1}-D[a-zA-Z-_\\.]*(?=(=))=")
                        listSecrets.each { secret ->
                            log.info "type: ${[password: secret].getClass()}"
                            log.info "type map: ${[[password: secret]].getClass()}"
                        }
                    }
                    toolsConfigure()
                }
            }
            stage('install dependencies') {
                steps {
                    sh 'mvn clean install -DskipTests'
                }
            }
            stage('compile') {
                steps {
                    sh 'mvn compile test-compile'
                }
            }
            stage('test') {
                steps {
                    runnerKarateTest(TAGS, ENVIRONMENT, JAVA_ARGS)
                }
            }
        }
        post {
            always {
                echo 'Pipeline completed!' // Print a message regardless of the outcome
            }
            success {
                echo 'Deployment to staging successful!' // Message on success
            }
            failure {
                echo 'Pipeline failed! Check the logs for errors.' // Message on failure
            }
        }
    }
}
