package com.deathrayresearch.dabu.shared.msg;

import com.deathrayresearch.dabu.shared.Document;

import java.time.ZonedDateTime;

/**
 *
 */
public class DocumentGetReply extends AbstractReply {

  Document document = null;

  public DocumentGetReply(Request request, Document document) {
    super(request);
    this.document = document;
  }

  public Document getDocument() {
    return document;
  }
}
