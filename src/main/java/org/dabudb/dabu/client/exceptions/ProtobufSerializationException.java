package org.dabudb.dabu.client.exceptions;

import com.google.common.base.Preconditions;
import org.dabudb.dabu.generated.protobufs.Request;

/**
 *  An exception occurred serializing or deserializing a document using ProtocolBuffers.
 */
public class ProtobufSerializationException extends SerializationException {

  public ProtobufSerializationException(Request.ErrorCondition errorCondition) {
    super(errorCondition);
    Preconditions.checkArgument(errorCondition.getErrorType()
        == Request.ErrorType.PROTOCOL_BUFFER_SERIALIZATION_EXCEPTION);
  }
}
