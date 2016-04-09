package com.deathrayresearch.dabu.shared.msg;

import java.util.List;

/**
 *
 */
public class DocsGetReply extends AbstractReply {

  List<byte[]> documents = null;

  public DocsGetReply(Request request, List<byte[]> documents) {
    super(request);
    this.documents = documents;
  }

  public List<byte[]> getDocuments() {
    return documents;
  }
}