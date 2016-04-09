package com.deathrayresearch.dabu.shared.msg;

/**
 *  Interface for a converter that serializes messages to bytes, and vice-versa.
 */
public interface MsgSerializerDeserializer {

  byte[] messageToBytes(Message message);

  Message bytesToMessage(byte[] messageBytes);

}
