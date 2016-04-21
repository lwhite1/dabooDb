package org.daboodb.daboo.shared.serialization.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import javax.annotation.concurrent.ThreadSafe;
import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Serializes ZonedDateTimes as ISO strings
 */
@ThreadSafe
class ZonedDateTimeSerializer implements JsonSerializer<ZonedDateTime> {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_ZONED_DATE_TIME;

  public JsonElement serialize(ZonedDateTime src,
                               Type typeOfSrc,
                               JsonSerializationContext context) {

    return new JsonPrimitive(src.format(FORMATTER));
  }
}


