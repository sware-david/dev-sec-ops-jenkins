import com.sware.core.services.EnvironmentManager
import com.sware.core.services.LogManager

def call() {
    EnvironManager.getEnv()
    LogManager.info("Environment: ${EnvironmentManager.getEnv()}")
    echo "get env"
}

return this