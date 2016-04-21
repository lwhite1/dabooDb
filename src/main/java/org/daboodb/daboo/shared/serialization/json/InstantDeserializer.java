package org.daboodb.daboo.shared.serialization.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import javax.annotation.concurrent.ThreadSafe;
import java.lang.reflect.Type;
import java.time.Instant;

/**
 * Deserializes Instants from longs (ms since epoch)
 */
@ThreadSafe
class InstantDeserializer implements JsonDeserializer<Instant> {

  public Instant deserialize(JsonElement json,
                             Type typeOfT,
                             JsonDeserializationContext context)
      throws JsonParseException {
    return Instant.parse(json.getAsString());
  }
}

