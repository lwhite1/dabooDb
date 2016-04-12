package org.dabudb.dabu.shared.serialization.json;

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
 * It has problems, though.
 * <p>
 * TODO(lwhite): Fix precision (and improve efficiency)
 * It should be possible to serialize to 2 longs (i.e. 16 bytes) and convert that to a (zero-padded) string (32 bytes),
 * as opposed to representing in ISO local date time format (38 bytes). The 2-long solution would be better in fact,
 * because ISO_LOCAL_DATE_TIME only has second precision, so we lose all sub-second precision (ms to nanos).
 * This solution should also be used for Instants and any other datetime format
 */
@ThreadSafe
class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

  public LocalDateTime deserialize(JsonElement json,
                                   Type typeOfT,
                                   JsonDeserializationContext context)
      throws JsonParseException {

    if (Strings.isNullOrEmpty(json.getAsString())) {
      return null;
    }
    return LocalDateTime.parse(json.getAsString(), FORMATTER);
  }
}


