package org.dabudb.dabu.client.exceptions;

import com.google.common.base.Preconditions;
import org.dabudb.dabu.shared.exceptions.DatastoreException;
import org.dabudb.dabu.generated.protobufs.Request;

/**
 *  An exception occurred serializing or deserializing a document
 */
public class SerializationException extends DatastoreException {

  public SerializationException(String message, Throwable cause) {
    super(message, cause);
  }
}
