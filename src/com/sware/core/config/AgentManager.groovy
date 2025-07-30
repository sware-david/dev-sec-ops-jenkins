package com.sware.core.config

import com.sware.core.logging.LogManager

class AgentManager implements Serializable {
    public static void configureAgent() {
        LogManager.info("Configuring agent: local_worker")
        this.agent { label 'local_worker' }
    }
}
