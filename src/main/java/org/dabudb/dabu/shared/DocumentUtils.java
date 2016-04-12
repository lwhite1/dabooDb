package org.dabudb.dabu.shared;

import com.google.protobuf.ByteString;
import org.dabudb.dabu.shared.protobufs.Request;

/**
 * Utilities for working with Documents
 */
public final class DocumentUtils {

  private DocumentUtils() {
  }

  public static Request.Document getDocument(Document document) {
    return Request.Document.newBuilder()
        .setKey(ByteString.copyFrom(document.key()))
        .setContentBytes(ByteString.copyFrom(document.contents()))
        .setContentClass(document.getContentClass())
        .setContentType(document.contentType())
        .setInstanceVersion(document.instanceVersion())
        .setSchemaVersion(document.schemaVersion())
        .build();
  }
}
