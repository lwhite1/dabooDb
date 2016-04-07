package com.deathrayresearch.dabu.shared.msg;

import com.deathrayresearch.dabu.shared.Document;

/**
 *
 */
public class DocumentGetRequest extends AbstractRequest {

  private final Document document;

  public DocumentGetRequest(Document document) {
    super(RequestType.WRITE);
    this.document = document;
  }

  public Document getDocument() {
    return document;
  }
}
