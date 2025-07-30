package development

import com.sware.core.services.LogManager
import com.sware.core.services.EnvironmentManager
import com.sware.core.config.AgentManager
import com.sware.core.secrets.CredentialManager
import com.sware.core.tasks.PostExecution

class MavenJUnitTest {
    def static logger = LogManager
    protected script
    private static String karateOpts = ""
    private static String jira = "-Djxray.update.evidence=true"

    MavenJUnitTest(script) {
        this.script = script
    }

    public void checkTools() {
        this.steps.sh {
            'mvn -v'
            'java --version'
        } 
    }

    public void configureEnv(String env) {
        this.script.steps {
            AgentManager.configureAgent()
            EnvironmentManager.setupEnvironment(env)
        }
    }

    public void installDependencies() {
        this.steps.sh {
            'mvn clean install -DskipTests'
        }
    }

    public void compileProject() {
        this.steps.sh {
            'mvn compile test-compile'
        }
    }

    public void runTestKarate(String tags) {
        this.steps {
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
            this.steps.sh "${command}"
        }
    }

    public void runPostTask() {
        this.steps {
            PostExecution.runPostTask()
        }
    }
}