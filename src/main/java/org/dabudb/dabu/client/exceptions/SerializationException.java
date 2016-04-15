package org.dabudb.dabu.client.exceptions;

import com.google.common.base.Preconditions;
import org.dabudb.dabu.shared.exceptions.DatastoreException;
import org.dabudb.dabu.generated.protobufs.Request;

/**
 *  An exception occurred serializing or deserializing a document using ProtocolBuffers.
 */
public class SerializationException extends DatastoreException {

  public SerializationException(Request.ErrorCondition errorCondition) {
    super(errorCondition);
    Preconditions.checkArgument(errorCondition.getErrorType()
        == Request.ErrorType.SERIALIZATION_EXCEPTION);
  }
}
