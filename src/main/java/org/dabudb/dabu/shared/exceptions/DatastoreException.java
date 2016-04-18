package org.dabudb.dabu.shared.exceptions;

/**
 *  General exception for an exception occurring during database operation
 */
public class DatastoreException extends Exception {

  public DatastoreException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new exception with the specified detail message.  The
   * cause is not initialized, and may subsequently be initialized by
   * a call to {@link #initCause}.
   *
   * @param message the detail message. The detail message is saved for
   *                later retrieval by the {@link #getMessage()} method.
   */
  public DatastoreException(String message) {
    super(message);
  }
}
