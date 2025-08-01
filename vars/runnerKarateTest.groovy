def call(String tags, String env, String javaArgs = "") {
    if (env.equals("qa")) {
        env = "cert"
    }
    karateOpts = "\"mvn test -Dkarate.options=--tags ${tags}\" -Dkarate.env=${env}"
    String command = "${karateOpts} ${javaArgs}"
    log.info("Running tests ...")

    sh "${command}"
}