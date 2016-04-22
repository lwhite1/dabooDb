package org.daboodb.daboo.shared.serialization;

import org.daboodb.daboo.shared.exceptions.RuntimeDatastoreException;

/**
 * Returns a serializer of the desired type
 */
public class ContentSerializerFactory {
  /**
   * Returns a content serializer of the type specified in the input
   */
  public static ContentSerializerDeserializer get(ContentSerializerType type) throws RuntimeDatastoreException {

    switch (type) {
      case JSON:
        return ContentJsonSerializer.get();
      default:
        throw new RuntimeDatastoreException("No Serializer available for specified type: " + type.name(), null);
    }
  }
}
