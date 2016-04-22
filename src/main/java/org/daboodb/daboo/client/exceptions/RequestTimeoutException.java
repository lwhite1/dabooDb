package org.daboodb.daboo.client.exceptions;

import org.daboodb.daboo.shared.exceptions.DatastoreException;

/**
 * The request failed to complete in the time allowed
 */
public class RequestTimeoutException extends DatastoreException {

  public RequestTimeoutException(String message, Throwable cause) {
    super(message, cause);
  }
}
