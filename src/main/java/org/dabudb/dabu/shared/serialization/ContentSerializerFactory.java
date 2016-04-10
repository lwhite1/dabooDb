package org.dabudb.dabu.shared.serialization;

/**
 *
 */
public class ContentSerializerFactory {
  /**
   * Returns a content serializer of the type specified in the input
   */
  public static ContentSerializerDeserializer get(ContentSerializerType type) {

    switch (type) {
      case JSON: return ContentJsonSerializer.get();
      default: throw new RuntimeException("No Serializer available for specified type: " + type.name());
    }
  }
}
