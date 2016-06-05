package org.daboodb.daboo.shared.serialization.json;

import com.google.common.base.Strings;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import javax.annotation.concurrent.ThreadSafe;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Deserializes a LocalDateTime from a String.
 * <p>
 * Note: While this is inefficient, it's (about) the best *simple* solution,
 * because a LocalDateTime in Java 8 is represented by two longs, so a primitive conversion will not work.
 */
@ThreadSafe
class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {

  public LocalDateTime deserialize(JsonElement json,
                                   Type typeOfT,
                                   JsonDeserializationContext context)
      throws JsonParseException {

    if (Strings.isNullOrEmpty(json.getAsString())) {
      return null;
    }
    return parseFromSerializationString(json.getAsString());
  }

  private LocalDateTime parseFromSerializationString(String serializedDateTime) {

    int year = Integer.valueOf(serializedDateTime.substring(0, 4));
    int month = Integer.valueOf(serializedDateTime.substring(4, 6));
    int day = Integer.valueOf(serializedDateTime.substring(6, 8));
    int hour = Integer.valueOf(serializedDateTime.substring(8, 10));
    int minute = Integer.valueOf(serializedDateTime.substring(10, 12));
    int second = Integer.valueOf(serializedDateTime.substring(12, 14));
    int nano = Integer.valueOf(serializedDateTime.substring(14));
    return LocalDateTime.of(year, month, day, hour, minute, second, nano);
  }
}


