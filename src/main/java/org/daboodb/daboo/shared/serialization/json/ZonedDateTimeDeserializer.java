package org.daboodb.daboo.shared.serialization.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import javax.annotation.concurrent.ThreadSafe;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Deserializes Strings to ZonedDateTime
 */
@ThreadSafe
class ZonedDateTimeDeserializer implements JsonDeserializer<ZonedDateTime> {

  public ZonedDateTime deserialize(JsonElement json,
                                   Type typeOfT,
                                   JsonDeserializationContext context)
      throws JsonParseException {

    return parseFromSerializationString(json.getAsString());
  }

  private ZonedDateTime parseFromSerializationString(String serializedDateTime) {

    int year = Integer.valueOf(serializedDateTime.substring(0, 4));
    int month = Integer.valueOf(serializedDateTime.substring(4, 6));
    int day = Integer.valueOf(serializedDateTime.substring(6, 8));
    int hour = Integer.valueOf(serializedDateTime.substring(8, 10));
    int minute = Integer.valueOf(serializedDateTime.substring(10, 12));
    int second = Integer.valueOf(serializedDateTime.substring(12, 14));
    int nano = Integer.valueOf(serializedDateTime.substring(14, 23));
    ZoneOffset zoneOffset = ZoneOffset.of(serializedDateTime.substring(23));
    return ZonedDateTime.of(year, month, day, hour, minute, second, nano, zoneOffset);
  }

}


