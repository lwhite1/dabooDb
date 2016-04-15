package org.dabudb.dabu.shared.serialization;

import org.dabudb.dabu.shared.exceptions.DatastoreRuntimeException;
import org.dabudb.dabu.shared.protobufs.Request;

/**
 * Returns a serializer of the desired type
 */
public class ContentSerializerFactory {
  /**
   * Returns a content serializer of the type specified in the input
   */
  public static ContentSerializerDeserializer get(ContentSerializerType type) throws RuntimeException {

    switch (type) {
      case JSON:
        return ContentJsonSerializer.get();
      default:
        throw new DatastoreRuntimeException(
            Request.ErrorCondition.newBuilder()
                .setErrorType(Request.ErrorType.SERIALIZATION_EXCEPTION)
                .setDescription("No Serializer available for specified type: " + type.name())
                .build());
    }
  }
}
