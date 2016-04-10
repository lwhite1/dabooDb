package org.dabudb.dabu.shared.serialization.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import javax.annotation.concurrent.ThreadSafe;
import java.lang.reflect.Type;
import java.time.LocalDate;

/**
 * Serializes localDates as longs
 */
@ThreadSafe
class LocalDateSerializer implements JsonSerializer<LocalDate> {

    public JsonElement serialize(LocalDate src,
                                 Type typeOfSrc,
                                 JsonSerializationContext context) {
        return new JsonPrimitive(src.toEpochDay());
    }
}