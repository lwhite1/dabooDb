package com.deathrayresearch.dabu.shared.msg.serialization;

import com.deathrayresearch.dabu.shared.DocumentContents;
import com.deathrayresearch.dabu.shared.msg.Message;
import com.deathrayresearch.dabu.shared.serialization.ContentSerializerDeserializer;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;

/**
 * A serializer that converts Document contents to json, then UTF_8 byte arrays
 */
public class MessageJsonSerializer implements MsgSerializerDeserializer {

  private static final Gson GSON = new Gson();

  private static final MessageJsonSerializer INSTANCE = new MessageJsonSerializer();

  private MessageJsonSerializer() {}

  public static MessageJsonSerializer get() {
    return INSTANCE;
  }

  @Override
  public byte[] serialize(Message message) {
    return GSON.toJson(message).getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public Message deserialize(Class clazz, byte[] messageBytes) {
    String json = new String(messageBytes, StandardCharsets.UTF_8);
    return (Message) GSON.fromJson(json, clazz);
  }
}
