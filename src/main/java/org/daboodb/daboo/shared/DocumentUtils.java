package org.daboodb.daboo.shared;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import org.daboodb.daboo.generated.protobufs.Request;
import org.daboodb.daboo.server.ServerSettings;

/**
 * Utilities for working with Documents
 */
public final class DocumentUtils {

  private DocumentUtils() {
  }

  public static Request.Document getDocument(Document document) {
    return Request.Document.newBuilder()
        .setKey(ByteString.copyFrom(document.getKey()))
        .setContentBytes(ByteString.copyFrom(document.serialized()))
        .setContentClass(document.getDocumentClass())
        .setContentType(document.getDocumentType())
        .setInstanceVersion(document.instanceVersion())
        .setSchemaVersion(document.schemaVersion())
        .build();
  }

  public static Document getDocumentFromRequestDoc(ByteString resultBytes) {



    Request.Document result;
    try {
      result = Request.Document.parseFrom(resultBytes);
    } catch (InvalidProtocolBufferException e) {
      e.printStackTrace();
      throw new RuntimeException("Unable to parse from protobuf");
    }

    Document document;
    try {
      document = ServerSettings.getInstance()
          .getDocumentSerializer()
            .bytesToDocument(Class.forName(result.getContentClass()),
                result.getContentBytes().toByteArray());
      if (document == null) {
        throw new RuntimeException("Failed to get document from DocumentFactory.");
      }
    }
    catch (ClassNotFoundException e) {
      throw new RuntimeException("Failed to get document from DocumentFactory.");
    }

    document.setDocumentClass(result.getContentClass());
    document.setDocumentType(result.getContentType());
    document.setKey(result.getKey().toByteArray());
    document.setInstanceVersion(result.getInstanceVersion());
    return document;
  }
}
