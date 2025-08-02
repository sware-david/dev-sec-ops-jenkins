package development

import com.sware.core.services.LogManager
import com.sware.core.services.EnvironmentManager
import com.sware.core.secrets.CredentialManager

class MavenJUnitTest implements Serializable {
    def logger = LogManager
    def steps
    private static String karateOpts = ""
    private static String jira = "-Djxray.update.evidence=true"

    MavenJUnitTest(steps) { this.steps = steps }

    public void configureEnv(String env) {
        steps {
            EnvironmentManager.setupEnvironment(env)
        }
    }

    public void installDependencies() {
        steps.sh {
            'mvn clean install -DskipTests'
        }
    }

    public void compileProject() {
        steps.sh {
            'mvn compile test-compile'
        }
    }

    public void runTestKarate(String tags) {
        steps {
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

            sh "${command}"
        }
    }
}