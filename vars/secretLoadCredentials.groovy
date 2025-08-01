def call() {
    def inputFile = new File("./src/test/resources/credential.json")
    def jsonSecrets = new JsonSlurper().parse(inputFile)
    log.info("Reading file complete")
    ArrayList credentialsToComplete = []

    jsonSecrets.each { value ->
        credentialsToComplete.add("${value.nameCredential}")
    }

    log.info("Credentials loaded")
    return secretLoadSecrets(credentialsToComplete)
}
