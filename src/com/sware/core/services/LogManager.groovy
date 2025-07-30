package com.sware.core.services

class LogManager implements Serializable {
    public static void info(String message) {
        println "Info: $message"
    }

    public static void error(String message) {
        System.err.println "Error: $message"
    }

    public static void warn(String message) {
        System.out.println "Warning: $message"
    }
}