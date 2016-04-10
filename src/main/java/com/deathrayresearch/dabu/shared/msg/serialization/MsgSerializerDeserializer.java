package com.deathrayresearch.dabu.shared.msg.serialization;

import com.deathrayresearch.dabu.shared.msg.Message;

/**
 *  Interface for a converter that serializes messages to bytes, and vice-versa.
 */
public interface MsgSerializerDeserializer {

  byte[] serialize(Message message);

  Message deserialize(Class clazz, byte[] messageBytes);

}
