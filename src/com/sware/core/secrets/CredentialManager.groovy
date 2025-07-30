package com.sware.core.secrets

import groovy.json.JsonSlurper

import com.sware.core.services.EnvironmentManager
import com.sware.core.logging.LogManager

class CredentialManager {

    private static String JAVA_ARGS = ""
    private static String jiraCredentials = "xray_saas_client_id;xray_saas_client_secret;jira_user;jira_token"

    public static String loadCredentials() {
        LogManager.info("Configuring file credentials")
        def inputFile = new File("./src/test/resources/credential.json")
        def jsonSecrets = new JsonSlurper().parse(inputFile)
        LogManager.info("Reading file complete")
        ArrayList credentialsToComplete = []

        inputJson.each { value ->
            credentialsToComplete.add("${value.nameCredential}")
        }

        loadSecrets(credentialsToComplete)
        LogManager.info("Credentials loaded")
        return JAVA_ARGS
    }

    private static void loadSecrets(ArrayList reqSecrets) {
        String env = EnvironmentManager.getEnv()
        for (requestSecret in reqSecrets) {
            LogManager.info("Loading... secret: ${requestSecret}")
            withCredentials([string(credentialsId: "${env}${requestSecret}", variable: 'secret_jenkins')]) {
                JAVA_ARGS += " -D${requestSecret}=${secret_jenkins}"
                LogManager.info("Load secret: ${requestSecret}")
            }
        }
    }

    public static String loadJiraXrayCredentials() {
        String env = EnvironmentManager.getEnv()
        String credentialsJiraXray = ""
        for (secretId in jiraCredentials.split(";")) {
            secretId = "${secretId}".toUpperCase()
            LogManager.info("Loading... jira secret: ${secretId}")

            withCredentials([string(credentialsId: "${secretId}", variable: 'secret_from_jenkins')]) {
                credentialsJiraXray += " -D${secretId}=${secret_from_jenkins}"
                LogManager.info("Load jira secret: ${secretId}")
            }
        }
    }
}
