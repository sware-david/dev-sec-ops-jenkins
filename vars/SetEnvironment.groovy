import com.sware.core.services.EnvironmentManager
import com.sware.core.services.LogManager

def call(envStr) {
    EnvironManager.setupEnvironment(envStr)
    LogManager.info("Environment set to: ${EnvironmentManager.getEnv()}")
    echo "set env"
}

return this