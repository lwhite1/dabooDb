package org.daboodb.daboo.shared.serialization.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;

/**
 * Tests the serialization and deserialization of ZonedDateTimes to and from json
 */
public class ZonedDateTimeSerializerTest {

  private GsonBuilder gsonBuilder = new GsonBuilder();

  @Test
  public void testSerialize() {
    gsonBuilder.registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeSerializer());
    gsonBuilder.registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeDeserializer());

    Gson gson = gsonBuilder.create();

    ZonedDateTime zonedDateTime = ZonedDateTime.of(LocalDate.now(), LocalTime.now(), ZoneId.of("GMT"));

    String serialized = gson.toJson(zonedDateTime);

    ZonedDateTime result = gson.fromJson(serialized, ZonedDateTime.class);

    assertEquals(zonedDateTime.getYear(), result.getYear());
    assertEquals(zonedDateTime.getMonthValue(), result.getMonthValue());
    assertEquals(zonedDateTime.getDayOfMonth(), result.getDayOfMonth());
    assertEquals(zonedDateTime.getHour(), result.getHour());
    assertEquals(zonedDateTime.getMinute(), result.getMinute());
    assertEquals(zonedDateTime.getSecond(), result.getSecond());
    assertEquals(zonedDateTime.getNano(), result.getNano());
    assertEquals(zonedDateTime.getOffset(), result.getOffset());
  }
}