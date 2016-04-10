package org.dabudb.dabu.shared.serialization.json;

import com.google.common.base.Strings;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import javax.annotation.concurrent.ThreadSafe;
import java.lang.reflect.Type;
import java.time.LocalDate;

/**
 * Deserializes localDates from longs
 */
@ThreadSafe
class LocalDateDeserializer implements JsonDeserializer<LocalDate> {

    public LocalDate deserialize(JsonElement json,
                                 Type typeOfT,
                                 JsonDeserializationContext context)
            throws JsonParseException {

        if (Strings.isNullOrEmpty(json.getAsString())) {
            return null;
        }
        return LocalDate.ofEpochDay(Long.parseLong(json.getAsString()));
    }
}

