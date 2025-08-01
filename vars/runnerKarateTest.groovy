def call(String tags, String env, String javaArgs = "") {
    if (env.equals("qa")) {
        env = "cert"
    }
    log.error "this a error message: $secret_from_jenkins"
    log.error "this a error message: $secret_jenkins"
    karateOpts = "mvn test \"-Dkarate.options=--tags ${tags}\" \"-Dkarate.env=${env}\""
    String command = "${karateOpts} ${javaArgs}"
    log.info("Running tests ...")

    sh "${command}"
}