package com.sware.core.config

import com.sware.core.services.LogManager

class ToolsManager implements Serializable {
    def steps

    ToolsManager(steps) { this.steps = steps }

    public void configureTools() {
        LogManager.info("Configuring tools: Maven and java")
        return this.steps.sh {
            'mvn -v'
            'java --version'
        }
    }
}
