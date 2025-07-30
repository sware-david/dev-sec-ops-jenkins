package development

import com.sware.core.services.LogManager
import com.sware.core.services.EnvironmentManager
import com.sware.core.config.AgentManager
import com.sware.core.secrets.CredentialManager
import com.sware.core.task.PostExecution

class MavenJUnitTest {
    def logger = LogManager
    private static String karateOpts = ""
    private static String jira = "-Djxray.update.evidence=true"

    public static void checkTools() {
        this.script.steps.sh 'mvn -v'
        this.script.steps.sh 'java --version'
    }

    public static void configureEnv(String env) {
        AgentManager.configureAgent()
        EnvironmentManager.setupEnvironment(env)

    }

    public static void installDependencies() {
        this.script.steps.sh 'mvn clean install -DskipTests'
    }

    public static void compileProject() {
        this.script.steps.sh 'mvn compile test-compile'
    }

    public static void runTestKarate(String tags) {
        logger.info("fetching comand to test ...")

        String runEnv = EnvironmentManager.getEnv()
        if (runEnv.equals("qa")) {
            runEnv = "cert"
        }
        karateOpts = "\"mvn test -Dkarate.options=--tags ${tags}\" -Dkarate.env=${runEnv}"
        karateOpts += CredentialManager.loadCredentials()
        jira += CredentialManager.loadJiraXrayCredentials()
        String command = "${karateOpts} ${jira}"
        logger.info("Running tests ...")
        this.script.steps.sh comand
    }

    public static void runPostTask() {
        PostExecution.runPostTask()
    }
}