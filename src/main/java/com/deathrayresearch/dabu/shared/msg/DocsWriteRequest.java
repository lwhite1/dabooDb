package com.deathrayresearch.dabu.shared.msg;

import com.deathrayresearch.dabu.shared.Document;
import com.google.common.primitives.SignedBytes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

/**
 *  Writes more than one document at a time in a single transactional batch
 */
public class DocsWriteRequest extends AbstractRequest {

  private final ArrayList<Document> documents = new ArrayList<>();

  public DocsWriteRequest(Collection<Document> documents) {
    super(RequestType.WRITE);
    this.documents.addAll(documents);
    this.documents.sort(new Comparator<Document>() {
      @Override
      public int compare(Document o1, Document o2) {
        return SignedBytes.lexicographicalComparator().compare(o1.key(), o2.key());
      }
    });
  }

  public Collection<Document> getDocuments() {
    return documents;
  }
}
