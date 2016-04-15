package org.dabudb.dabu.client.exceptions;

import com.google.common.base.Preconditions;
import org.dabudb.dabu.shared.protobufs.Request;

/**
 *  An exception occurred serializing or deserializing a document using Json.
 */
public class JsonSerializationException extends SerializationException {

  public JsonSerializationException(Request.ErrorCondition errorCondition) {
    super(errorCondition);
    Preconditions.checkArgument(errorCondition.getErrorType()
        == Request.ErrorType.JSON_SERIALIZATION_EXCEPTION);
  }
}
