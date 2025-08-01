def call(ArrayList reqSecrets, String env) {
    def JAVA_ARGS
    for (requestSecret in reqSecrets) {
        log.info("Loading... secret: ${requestSecret}")
        withCredentials([string(credentialsId: "${env}${requestSecret}", variable: 'secret_jenkins')]) {
            JAVA_ARGS += " -D${requestSecret}=${secret_jenkins}"
            log.info("Load secret: ${requestSecret}")
        }
    }
    return JAVA_ARGS
}
