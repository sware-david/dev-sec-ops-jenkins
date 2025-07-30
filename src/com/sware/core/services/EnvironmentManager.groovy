package com.sware.core.services

import com.sware.core.logging.LogManager

class EnvironmentManager {

    private static String environment = "dev"

    public static String getEnv() {
        return environment
    }

    public static void setupEnvironment(String env) {
        if (env.equals("dev") || env.equals("qa")) {
            environment = env
            LogManager.info("Environment configured: ${environment}")
        } else {
            LogManager.warn("Environment not suported, setting by default 'dev'")
        }
    }
}
