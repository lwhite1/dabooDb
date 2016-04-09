package com.deathrayresearch.dabu.shared.msg;

import com.deathrayresearch.dabu.shared.Document;

import java.time.ZonedDateTime;

/**
 *
 */
public class DocGetReply extends AbstractReply {

  byte[] document = null;

  public DocGetReply(Request request, byte[] document) {
    super(request);
    this.document = document;
  }

  public byte[] getDocument() {
    return document;
  }
}
