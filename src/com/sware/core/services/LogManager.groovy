package com.sware.core.services

class LogManager {

    static void info(String message) {
        println "Info: $message"
    }

    static void error(String message) {
        System.err.println "Error: $message"
    }

    static void warn(String message) {
        System.out.println "Warning: $message"
    }
}