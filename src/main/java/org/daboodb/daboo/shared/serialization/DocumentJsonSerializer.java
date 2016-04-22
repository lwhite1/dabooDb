package org.daboodb.daboo.shared.serialization;

import com.google.gson.Gson;
import org.daboodb.daboo.shared.Document;

import java.nio.charset.StandardCharsets;

/**
 *
 */
public class DocumentJsonSerializer implements DocumentSerializer {

  private static final Gson GSON = new Gson();

  @Override
  public byte[] documentToBytes(Document document) {
    return GSON.toJson(document).getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public Document bytesToDocument(Class documentClass, byte[] documentBytes) {
    return (Document) GSON.fromJson(new String(documentBytes, StandardCharsets.UTF_8), documentClass);
  }
}
