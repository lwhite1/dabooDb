package org.dabudb.dabu.shared.msg.request;

import org.dabudb.dabu.shared.Document;

/**
 * A request to write a single document
 */
public class DocWriteRequest extends AbstractRequest {

  private final byte[] key;
  private final byte[] documentBytes;

  public DocWriteRequest(Document document) {
    super(RequestType.DOCUMENT_WRITE);
    this.key = document.key();
    this.documentBytes = document.marshall();
  }

  public byte[] getKey() {
    return key;
  }

  public byte[] getDocumentBytes() {
    return documentBytes;
  }
}
