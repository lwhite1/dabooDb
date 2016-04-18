package org.dabudb.dabu.client.exceptions;

import org.dabudb.dabu.shared.exceptions.DatastoreException;

/**
 * The request failed to complete in the time allowed
 */
public class RequestTimeoutException extends DatastoreException {

  public RequestTimeoutException(String message, Throwable cause) {
    super(message, cause);
  }
}
