package org.daboodb.daboo.shared.serialization.json;

import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import javax.annotation.concurrent.ThreadSafe;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Serializes LocalDateTimes to Strings.
 */
@ThreadSafe
class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {

  public JsonElement serialize(LocalDateTime src,
                               Type typeOfSrc,
                               JsonSerializationContext context) {

    return new JsonPrimitive(toSortableString(src));
  }

  /**
   * Returns a string representation of the given {@link LocalDateTime} in nano precision,
   * formatted such that a standard string sort will sort in temporal order
   */
  private static String toSortableString(LocalDateTime localDateTime) {
    return new StringBuilder("")
        .append(Strings.padStart(String.valueOf(localDateTime.getYear()), 4, '0'))
        .append(Strings.padStart(String.valueOf(localDateTime.getMonthValue()), 2, '0'))
        .append(Strings.padStart(String.valueOf(localDateTime.getDayOfMonth()), 2, '0'))
        .append(Strings.padStart(String.valueOf(localDateTime.getHour()), 2, '0'))
        .append(Strings.padStart(String.valueOf(localDateTime.getMinute()), 2, '0'))
        .append(Strings.padStart(String.valueOf(localDateTime.getSecond()), 2, '0'))
        .append(Strings.padStart(String.valueOf(localDateTime.getNano()), 9, '0'))
        .toString();
  }
}


