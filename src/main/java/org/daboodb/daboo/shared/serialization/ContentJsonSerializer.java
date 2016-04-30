package org.daboodb.daboo.shared.serialization;

import org.daboodb.daboo.shared.Document;
import com.google.gson.Gson;
import org.daboodb.daboo.shared.serialization.json.GsonFactory;

import java.nio.charset.StandardCharsets;

/**
 * A serializer that converts Document serialized to json, then UTF_8 byte arrays
 */
public class ContentJsonSerializer implements ContentSerializerDeserializer {

  private static final Gson GSON = GsonFactory.INSTANCE.defaultGson();

  private static final ContentJsonSerializer INSTANCE = new ContentJsonSerializer();

  private ContentJsonSerializer() {
  }

  public static ContentJsonSerializer get() {
    return INSTANCE;
  }

  @Override
  public byte[] serialize(Document contents) {
    return GSON.toJson(contents).getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public Document deserialize(Class clazz, byte[] contentBytes) {
    String json = new String(contentBytes, StandardCharsets.UTF_8);
    return (Document) GSON.fromJson(json, clazz);
  }
}
