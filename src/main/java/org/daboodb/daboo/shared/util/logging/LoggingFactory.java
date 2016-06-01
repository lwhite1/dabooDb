package org.daboodb.daboo.shared.util.logging;

/**
 * Clients use this factory to get a specific logger.
 * The logger compiles to the Logger interface
 * but it's concrete implementation is defined by configuration
 * where that implementation could be:
 *
 * SLF4j
 * Logback
 * Apache log4j 1.2
 * Apache log4j 2
 * Java Util Logging
 * Apache Common Logging
 *
 * or any other logging framework. That logging framework is wrapped
 * using the adaptor pattern allowing any logging framework to be easily used.
 */
public class LoggingFactory {

    /**
     * Gets the logger for a particular class
     *
     * @param clientClass the class that wants to do logging.
     * @return a Logger implementation
     */
    public static LoggerWriter getLogger(Class clientClass) {
        return new Log4jLogger(clientClass);

    }
}
