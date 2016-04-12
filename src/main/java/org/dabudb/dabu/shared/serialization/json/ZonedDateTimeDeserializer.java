package org.dabudb.dabu.shared.serialization.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import javax.annotation.concurrent.ThreadSafe;
import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Deserializes ISO Strings to ZonedDateTime
 */
@ThreadSafe
class ZonedDateTimeDeserializer implements JsonDeserializer<ZonedDateTime> {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_ZONED_DATE_TIME;

  public ZonedDateTime deserialize(JsonElement json,
                                   Type typeOfT,
                                   JsonDeserializationContext context)
      throws JsonParseException {

    return ZonedDateTime.parse(json.getAsString(), FORMATTER);
  }
}


