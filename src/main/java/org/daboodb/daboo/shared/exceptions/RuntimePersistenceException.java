package org.daboodb.daboo.shared.exceptions;

/**
 * Thrown when an exception occurs during persistence that is not covered by any more specific exception case.
 */
public class RuntimePersistenceException extends RuntimeDatastoreException {

  public RuntimePersistenceException(String message, Exception cause) {
    super(message, cause);
  }
}
