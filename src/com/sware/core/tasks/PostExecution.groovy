package com.sware.core.tasks

class PostExecution {
    public static void runPostTask() {
        always {
            echo "Pipeline completed!"
        }
        success {
            echo "Pipeline success! Deployment to staging successful!"
        }
        failure {
            echo "Pipeline failed! Check the logs for errors."
        }
    }
}