package org.daboodb.daboo.shared.util.logging;

/**
 * Vanilla logging api (interface)
 */
public interface LoggerWriter {

    void logError(String message, Exception e);
    void logError(String message);

    void logWarning(String message,Exception e);
    void logWarning(String message );

    void logInfo(String message );

    void logDebug(String message, Exception e);
    void logDebug(String message);

}
