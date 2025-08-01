def call(String env = "dev") {
    def ENVIRONMENT = env.toLowerCase()
    environmentValidate(envStr)

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
                    JAVA_ARGS += secretLoadCredentials()
                    JAVA_ARGS += secretLoadJiraXrayCredentials()
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
