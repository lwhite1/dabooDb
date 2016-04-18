package org.dabudb.dabu.shared.exceptions;

/**
 *  General unchecked exception for a system or JVM exception occurring during a database operation
 */
public class RuntimeRequestException extends RuntimeDatastoreException {

  private final byte[] requestId;

  public RuntimeRequestException(String message, Throwable cause, byte[] requestId) {
    super(message, cause);
    this.requestId = requestId;
  }

  public byte[] getRequestId() {
    return requestId;
  }
}
