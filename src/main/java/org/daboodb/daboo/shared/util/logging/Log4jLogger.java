package org.daboodb.daboo.shared.util.logging;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Adaptor for Log4j v2 - wraps our logging interface around
 * org.apache.logging.log4j
 */
public class Log4jLogger implements LoggerWriter {

    private static Logger log4jLogger;

    /**
     * Create a new logger for the specified class.
     *
     * @param clientClass - the class that wants do the logging
     */
    Log4jLogger(Class clientClass) {
        log4jLogger = LogManager.getLogger(clientClass);
    }

    @Override
    public void logError(String message, Exception e) {
        log4jLogger.log(Level.ERROR,message,e);
    }

    @Override
    public void logError(String message) {
        log4jLogger.log(Level.ERROR,message);
    }

    @Override
    public void logWarning(String message, Exception e) {
        log4jLogger.log(Level.WARN,message,e);
    }

    @Override
    public void logWarning(String message) {
        log4jLogger.log(Level.WARN,message);
    }

    @Override
    public void logInfo(String message) {
        log4jLogger.log(Level.INFO,message);
    }

    @Override
    public void logDebug(String message, Exception e) {
        log4jLogger.log(Level.DEBUG,message,e);
    }

    @Override
    public void logDebug(String message) {
        log4jLogger.log(Level.DEBUG,message);
    }
}
