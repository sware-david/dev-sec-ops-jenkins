def call(String jiraCredentials = "xray_saas_client_id;xray_saas_client_secret;jira_user;jira_token") {
    String credentialsJiraXray = ""
    for (secretId in jiraCredentials.split(";")) {
        secretId = "${secretId}".toUpperCase()
        log.info("Loading... jira secret: ${secretId}")

        withCredentials([string(credentialsId: "${secretId}", variable: 'secret_from_jenkins')]) {
            credentialsJiraXray += " -D${secretId}=${secret_from_jenkins}"
            log.info("Load jira secret: ${secretId}, $secret_from_jenkins")
        }
    }
    return credentialsJiraXray
}
