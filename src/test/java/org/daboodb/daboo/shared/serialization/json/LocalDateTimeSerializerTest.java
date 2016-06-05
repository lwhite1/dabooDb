package org.daboodb.daboo.shared.serialization.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import org.junit.Test;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Tests the serialization and deserialization of LocalDateTimes to and from json
 */
public class LocalDateTimeSerializerTest {

  private GsonBuilder gsonBuilder = new GsonBuilder();

  @Test
  public void testSerialize() {
    gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
    gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer());

    Gson gson = gsonBuilder.create();

    LocalDateTime localDateTime = LocalDateTime.now();

    String serialized = gson.toJson(localDateTime);

    assertEquals(localDateTime, gson.fromJson(serialized, LocalDateTime.class));
  }
}