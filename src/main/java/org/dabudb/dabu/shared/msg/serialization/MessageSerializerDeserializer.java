package org.dabudb.dabu.shared.msg.serialization;

import org.dabudb.dabu.shared.msg.Message;

/**
 *  Interface for a converter that serializes messages to bytes, and vice-versa.
 */
public interface MessageSerializerDeserializer {

  byte[] serialize(Message message);

  Message deserialize(Class clazz, byte[] messageBytes);

}
