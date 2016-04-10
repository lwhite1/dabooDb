package org.dabudb.dabu.shared.serialization.json;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * A schema definition object for GsonBuilders
 */
public class GsonSchema {

  private final int schemaVersion;

  private final Map<Class, JsonDeserializer> deserializers;
  private final Map<Class, JsonSerializer> serializers;

  public static Builder builder(int schemaVersion) {
    return new Builder(schemaVersion);
  }

  private GsonSchema(Builder builder) {
    this.schemaVersion = builder.schemaVersion;
    this.deserializers = builder.deserializers;
    this.serializers = builder.serializers;
  }

  public Map<Class, JsonDeserializer> getDeserializers() {
    return deserializers;
  }

  public Map<Class, JsonSerializer> getSerializers() {
    return serializers;
  }

  public int getSchemaVersion() {
    return schemaVersion;
  }

  public final static class Builder {

    private final int schemaVersion;
    private final Map<Class, JsonSerializer> serializers = new HashMap<>();
    private final Map<Class, JsonDeserializer> deserializers = new HashMap<>();

    public Builder(int schemaVersion) {
      this.initialize();
      this.schemaVersion = schemaVersion;
    }

    public GsonSchema build() {
      return new GsonSchema(this);
    }

    public Builder register(Class clazz, JsonSerializer serializer) {
      serializers.put(clazz, serializer);
      return this;
    }

    public Builder register(Class clazz, JsonDeserializer deserializer) {
      deserializers.put(clazz, deserializer);
      return this;
    }

    private void initialize() {

      // register serializers and deserializers
      JsonSerializer localDateSerializer = new LocalDateSerializer();
      register(LocalDate.class, localDateSerializer);

      JsonSerializer localDateTimeSerializer = new LocalDateTimeSerializer();
      register(LocalDateTime.class, localDateTimeSerializer);

      JsonSerializer instantSerializer = new InstantSerializer();
      register(Instant.class, instantSerializer);

      JsonSerializer zonedDateTimeSerializer = new ZonedDateTimeSerializer();
      register(ZonedDateTime.class, zonedDateTimeSerializer);

      JsonDeserializer localDateDeserializer = new LocalDateDeserializer();
      register(LocalDate.class, localDateDeserializer);

      JsonDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer();
      register(LocalDateTime.class, localDateTimeDeserializer);

      JsonDeserializer instantDeserializer = new InstantDeserializer();
      register(Instant.class, instantDeserializer);

      JsonDeserializer zonedDateTimeDeserializer = new ZonedDateTimeDeserializer();
      register(ZonedDateTime.class, zonedDateTimeDeserializer);
    }
  }
}
