package org.dabudb.dabu.shared.serialization.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import javax.annotation.concurrent.ThreadSafe;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Serializes LocalDateTimes to ISO Strings.
 */
@ThreadSafe
class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

  public JsonElement serialize(LocalDateTime src,
                               Type typeOfSrc,
                               JsonSerializationContext context) {

    return new JsonPrimitive(src.format(FORMATTER));
  }
}


