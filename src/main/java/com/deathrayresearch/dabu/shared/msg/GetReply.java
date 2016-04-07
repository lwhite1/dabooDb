package com.deathrayresearch.dabu.shared.msg;

import com.deathrayresearch.dabu.shared.Document;

import java.time.ZonedDateTime;

/**
 *
 */
public class GetReply extends AbstractReply {

  Document document = null;

  public GetReply(Request request, Document document) {
    super(request);
    this.document = document;
  }

  public Document getDocument() {
    return document;
  }
}
