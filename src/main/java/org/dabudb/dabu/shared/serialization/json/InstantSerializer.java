package org.dabudb.dabu.shared.serialization.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import javax.annotation.concurrent.ThreadSafe;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Serializes Instants to longs (ms since epoch)
 */
@ThreadSafe
class InstantSerializer implements JsonSerializer<Instant> {

  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_INSTANT;

  public JsonElement serialize(Instant src,
                               Type typeOfSrc,
                               JsonSerializationContext context) {
    return new JsonPrimitive(FORMATTER.format(src));
  }
}


