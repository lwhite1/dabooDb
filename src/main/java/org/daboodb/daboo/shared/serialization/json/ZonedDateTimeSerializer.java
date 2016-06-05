package org.daboodb.daboo.shared.serialization.json;

import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import javax.annotation.concurrent.ThreadSafe;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Serializes ZonedDateTimes as strings
 */
@ThreadSafe
class ZonedDateTimeSerializer implements JsonSerializer<ZonedDateTime> {

  public JsonElement serialize(ZonedDateTime src,
                               Type typeOfSrc,
                               JsonSerializationContext context) {

    return new JsonPrimitive(toSortableString(src));
  }

  /**
   * Returns a string representation of the given {@link ZonedLocalDateTime} in nano precision,
   * formatted such that a standard string sort will sort in temporal order
   */
  private static String toSortableString(ZonedDateTime zonedDateTime) {
    return new StringBuilder("")
        .append(Strings.padStart(String.valueOf(zonedDateTime.getYear()), 4, '0'))
        .append(Strings.padStart(String.valueOf(zonedDateTime.getMonthValue()), 2, '0'))
        .append(Strings.padStart(String.valueOf(zonedDateTime.getDayOfMonth()), 2, '0'))
        .append(Strings.padStart(String.valueOf(zonedDateTime.getHour()), 2, '0'))
        .append(Strings.padStart(String.valueOf(zonedDateTime.getMinute()), 2, '0'))
        .append(Strings.padStart(String.valueOf(zonedDateTime.getSecond()), 2, '0'))
        .append(Strings.padStart(String.valueOf(zonedDateTime.getNano()), 9, '0'))
        .append(zonedDateTime.getOffset().toString())
        .toString();
  }
}


