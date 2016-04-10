package org.dabudb.dabu.shared;

import com.google.gson.Gson;

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
