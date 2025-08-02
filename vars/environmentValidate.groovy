def call(String env) {
    if (env == null || env.isEmpty()) {
        log.warn("Environment is null or empty, setting to default 'dev'")
        environment = "dev"
    } else if (env.equals("dev") || env.equals("qa")) {
        environment = env
        log.info("Environment configured: ${environment}")
    } else {
        log.warn("Environment not suported, setting by default 'dev'")
    }
}
