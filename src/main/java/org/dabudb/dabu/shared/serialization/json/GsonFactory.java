package org.dabudb.dabu.shared.serialization.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import java.util.HashMap;
import java.util.Map;

/**
 * A factory for creating Gson instances that conform to a specific schema
 * These schemas handle timestamps consistently, support common serializers, etc.
 *
 * We distinguish between schemas used for message serialization and those used for document content serialization.
 */
@ThreadSafe
public class GsonFactory {

  /**
   * A standard Gson object that is used for serializing and deserialzing requests and replies
   */
  private Gson messageGson = defaultGson();

  /**
   * Maps from a schemaVersion int to a constructed Gson instance that embodies that version.
   * This is essentially a cache for the Gson instance, so we don't have to construct one from the
   * corresponding schema whenever it's needed. Corresponding schemas are held in the contentSchemaVersions map.
   */
  @GuardedBy("this")
  private final HashMap<Integer, Gson> contentGsonVersions = new HashMap<>();

  /**
   * Maps from a schemaVersion int to a GsonSchema that embodies that version.
   * This Schema is used to create an appropriate gson instance for the schema version
   */
  @GuardedBy("this")
  private final HashMap<Integer, GsonSchema> contentSchemaVersions = new HashMap<>();

  public static final GsonFactory INSTANCE = new GsonFactory();

  private GsonFactory() {}

  /**
   * Syntactic sugar for registering a new content schema
   */
  public static void registerSchema(GsonSchema newSchema) {
    INSTANCE.register(newSchema);
  }

  /**
   * Returns a gson object initialized to handle serialization for the given schema version
   */
  public static Gson gson(int schemaVersion) {
    return INSTANCE.gsonForSchemaVersion(schemaVersion);
  }

  /**
   * Registers a new schema, merging it into an existing schema for that version, if one already
   * exists
   */
  public synchronized void register(GsonSchema newSchema) {

    GsonSchema oldSchema = contentSchemaVersions.get(newSchema.getSchemaVersion());
    GsonSchema updatedSchema;

    if (oldSchema != null) {
      GsonSchema.Builder builder = new GsonSchema.Builder(newSchema.getSchemaVersion());

      for (Map.Entry<Class, JsonSerializer> entry : oldSchema.getSerializers().entrySet()) {
        builder.register(entry.getKey(), entry.getValue());
      }

      for (Map.Entry<Class, JsonDeserializer> entry : oldSchema.getDeserializers().entrySet()) {
        builder.register(entry.getKey(), entry.getValue());
      }

      for (Map.Entry<Class, JsonSerializer> entry : newSchema.getSerializers().entrySet()) {
        builder.register(entry.getKey(), entry.getValue());
      }

      for (Map.Entry<Class, JsonDeserializer> entry : newSchema.getDeserializers().entrySet()) {
        builder.register(entry.getKey(), entry.getValue());
      }

      updatedSchema = builder.build();

    } else {
      updatedSchema = newSchema;
    }
    contentSchemaVersions.put(updatedSchema.getSchemaVersion(), updatedSchema);

    Gson gson = createGsonFromSchema(updatedSchema);

    contentGsonVersions.put(newSchema.getSchemaVersion(), gson);
  }

  /**
   * Returns a GSON initialized with a standard set of serializers/deserializers, plus type
   * adapters for serializing query-related objects (e.g. Terms, Queries, Constants, Expressions,
   * etc.)
   * <p>
   * Not for use by worldview client applications, as it contains no serialization capability
   * for application specific Payload objects
   */
  public synchronized Gson defaultGson() {
    return createGsonFromSchema(defaultSchema());
  }

  /**
   * Returns a Gson object initialized to the given version, creating such an object based on
   * the default schema, if it does not already exist
   */
  @Nullable
  public synchronized Gson gsonForSchemaVersion(int schemaVersion) {
    Gson result = contentGsonVersions.get(schemaVersion);
    if (result == null) {
      result = createGsonFromSchema(GsonSchema.builder(schemaVersion).build());
      contentGsonVersions.put(schemaVersion, result);
    }
    return result;
  }

  private GsonSchema defaultSchema() {
    GsonSchema.Builder builder = new GsonSchema.Builder(1);
    return builder.build();
  }

  private Gson createGsonFromSchema(GsonSchema schema) {
    GsonBuilder builder = new GsonBuilder();
    builder.setVersion(schema.getSchemaVersion());
    builder.setDateFormat("yyyy-MM-dd HH:mm:ss.S");
    registerSerializers(schema, builder);
    return builder.create();
  }

  public Gson getMessageGson() {
    return messageGson;
  }

  private void registerSerializers(GsonSchema schema, GsonBuilder builder) {

    for (Map.Entry<Class, JsonSerializer> entry : schema.getSerializers().entrySet()) {
      builder.registerTypeAdapter(entry.getKey(), entry.getValue());
    }

    for (Map.Entry<Class, JsonDeserializer> entry : schema.getDeserializers().entrySet()) {
      builder.registerTypeAdapter(entry.getKey(), entry.getValue());
    }
  }

}
