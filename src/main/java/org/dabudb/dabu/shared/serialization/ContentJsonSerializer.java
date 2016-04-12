package org.dabudb.dabu.shared.serialization;

import org.dabudb.dabu.shared.DocumentContents;
import com.google.gson.Gson;
import org.dabudb.dabu.shared.serialization.json.GsonFactory;

import java.nio.charset.StandardCharsets;

/**
 * A serializer that converts Document contents to json, then UTF_8 byte arrays
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
  public byte[] serialize(DocumentContents contents) {
    return GSON.toJson(contents).getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public DocumentContents deserialize(Class clazz, byte[] contentBytes) {
    String json = new String(contentBytes, StandardCharsets.UTF_8);
    return (DocumentContents) GSON.fromJson(json, clazz);
  }
}
