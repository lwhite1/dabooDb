package org.dabudb.dabu.shared.msg.serialization;

/**
 *
 */
public class MessageSerializerFactory {
  /**
   * Returns a content serializer of the type specified in the input
   */
  public static MessageSerializerDeserializer get(MessageSerializerType type) {

    switch (type) {
      case JSON: return MessageJsonSerializer.get();
      default: throw new RuntimeException("No Serializer available for specified type: " + type.name());
    }
  }
}
