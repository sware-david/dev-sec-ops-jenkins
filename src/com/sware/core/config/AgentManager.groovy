import com.sware.core.logging.LogManager

package com.sware.core.config

class AgentManager {
    static void configureAgent() {
        LogManager.info("Configuring agent: local_worker")
        this.agent { label 'local_worker' }
    }
}
