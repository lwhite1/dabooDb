package com.deathrayresearch.dabu.shared.msg;

import com.deathrayresearch.dabu.shared.Document;

import java.util.Collection;

/**
 *  Writes more than one document at a time in a single transactional batch
 */
public class DocsWriteRequest extends AbstractRequest {

  private final Collection<Document> documents;

  public DocsWriteRequest(Collection<Document> documents) {
    super(RequestType.WRITE);
    this.documents = documents;
  }

  public Collection<Document> getDocuments() {
    return documents;
  }
}
