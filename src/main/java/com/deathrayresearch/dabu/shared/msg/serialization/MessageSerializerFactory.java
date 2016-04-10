package com.deathrayresearch.dabu.shared.msg.serialization;

import com.deathrayresearch.dabu.shared.serialization.ContentJsonSerializer;
import com.deathrayresearch.dabu.shared.serialization.ContentSerializerDeserializer;
import com.deathrayresearch.dabu.shared.serialization.ContentSerializerType;

/**
 *
 */
public class MessageSerializerFactory {
  /**
   * Returns a content serializer of the type specified in the input
   */
  public static MsgSerializerDeserializer get(MsgSerializerType type) {

    switch (type) {
      case JSON: return MessageJsonSerializer.get();
      default: throw new RuntimeException("No Serializer available for specified type: " + type.name());
    }
  }
}
