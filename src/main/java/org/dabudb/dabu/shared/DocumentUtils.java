package org.dabudb.dabu.shared;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import org.dabudb.dabu.generated.protobufs.Request;

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

  public static Document getDocumentFromRequestDoc(Class documentClass, ByteString resultBytes) {

    Document document = DocumentFactory.documentForClass(documentClass);

    if (document == null) {
      throw new RuntimeException("Failed to get document from DocumentFactory.");
    }

    Request.Document result;
    try {
      result = Request.Document.parseFrom(resultBytes);
    } catch (InvalidProtocolBufferException e) {
      e.printStackTrace();
      throw new RuntimeException("Unable to parse from protobuf");
    }
    document.setContentClass(result.getContentClass());
    document.setContentType(result.getContentType());
    document.setKey(result.getKey().toByteArray());
    document.setSchemaVersion((short) result.getSchemaVersion());
    document.setInstanceVersion(result.getInstanceVersion());
    document.setContents(result.getContentBytes().toByteArray());
    return document;
  }
}
