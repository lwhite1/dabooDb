package com.deathrayresearch.dabu.client.serialization;

import com.deathrayresearch.dabu.shared.DocumentContents;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;

/**
 * A serializer that converts Document contents to json, then UTF_8 byte arrays
 */
public class JsonSerializerDeserializer implements ContentSerializerDeserializer {

  private static final Gson GSON = new Gson();

  private static final JsonSerializerDeserializer INSTANCE = new JsonSerializerDeserializer();

  private JsonSerializerDeserializer() {}

  public static JsonSerializerDeserializer get() {
    return INSTANCE;
  }

  @Override
  public byte[] serialize(DocumentContents contents) {
    return GSON.toJson(contents).getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public DocumentContents deserialize(Class clazz, byte[] contentBytes) {
    String json = new String(contentBytes, StandardCharsets.UTF_8);
    return (DocumentContents) GSON.fromJson(json, clazz);
  }
}
