package org.dabudb.dabu.client.exceptions;

import org.dabudb.dabu.shared.exceptions.RuntimeDatastoreException;

/**
 *  An exception occurred serializing or deserializing a document
 */
public class RuntimeSerializationException extends RuntimeDatastoreException {

  public RuntimeSerializationException(String message, Throwable cause) {
    super(message, cause);
  }
}
