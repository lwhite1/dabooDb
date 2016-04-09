package com.deathrayresearch.dabu.shared.msg;

import java.util.List;

/**
 * A reply to a request to get a list of documents according to their keys.
 * It contains the documents matching all the keys that it could find.
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
