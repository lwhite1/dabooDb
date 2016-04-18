package org.dabudb.dabu.shared.exceptions;

/**
 *  General exception for an exception occurring during database operation
 */
public class DatastoreException extends Exception {

  public DatastoreException(String message, Throwable cause) {
    super(message, cause);
  }
}
