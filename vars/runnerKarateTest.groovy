def call(String tags, String env, String secretArgs = "") {
    if (env.equals("qa")) {
        env = "cert"
    }
    karateOpts = "mvn test \"-Dkarate.options=--tags ${tags}\" \"-Dkarate.env=${env}\""
    String command = "${karateOpts} -Djxray.update.evidence=true ${secretArgs}"
    log.info("Running tests ...")

    String[] listSecrets = secretArgs.trim().split(" {0,1}-D[a-zA-Z-_\\.]*(?=(=))=")
    List<HashMap> mapPasswords = new ArrayList<>()

    listSecrets.each { secret ->
        mapPasswords.add([password: secret])
    }

    wrap([$class: 'MaskPasswordsBuildWrapper', varPasswordPairs: mapPasswords]) {
        sh "${command}"
    }
}