def call() {
    sh {
        'mvn -v'
        'java --version'
    }
}
