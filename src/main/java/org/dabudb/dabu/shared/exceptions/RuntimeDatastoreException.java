package org.dabudb.dabu.shared.exceptions;

import org.dabudb.dabu.generated.protobufs.Request;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 *  General unchecked exception for a system or JVM exception occurring during a database operation
 */
public class RuntimeDatastoreException extends RuntimeException {

  private Request.ErrorOrigin origin;
  private Request.ErrorType type;
  private byte[] token;

  public RuntimeDatastoreException(String message, Throwable cause) {
    super(message, cause);

    if (! (cause instanceof RuntimeDatastoreException)
        && ! (cause instanceof DatastoreException)) {
      token = UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8);
    }
  }

  /**
   * Constructs a new runtime exception with the specified detail message.
   * The cause is not initialized, and may subsequently be initialized by a
   * call to {@link #initCause}.
   *
   * @param message the detail message. The detail message is saved for
   *                later retrieval by the {@link #getMessage()} method.
   */
  public RuntimeDatastoreException(String message) {
    super(message);
  }

  public Request.ErrorOrigin getOrigin() {
    return origin;
  }

  public Request.ErrorType getType() {
    return type;
  }

  public byte[] getToken() {
    return token;
  }
}
