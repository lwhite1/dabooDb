package com.deathrayresearch.dabu.shared.msg;

import com.deathrayresearch.dabu.shared.Document;

/**
 * A request to write a single document
 */
public class DocWriteRequest extends AbstractRequest {

  private final Document document;

  public DocWriteRequest(Document document) {
    super(RequestType.DOCUMENT_WRITE);
    this.document = document;
  }

  public Document getDocument() {
    return document;
  }
}
