package org.dabudb.dabu.shared.msg.request;

import org.dabudb.dabu.shared.Document;
import com.google.common.primitives.SignedBytes;

import javax.annotation.concurrent.Immutable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *  A request to write a collection of documents at a time in a single transactional batch
 */
@Immutable
public final class DocsWriteRequest extends AbstractRequest {

  private final ArrayList<Document> documents = new ArrayList<>();

  public DocsWriteRequest(List<Document> documents) {
    super(RequestType.DOCUMENTS_WRITE);
    this.documents.addAll(documents);
    this.documents.sort(new Comparator<Document>() {
      @Override
      public int compare(Document o1, Document o2) {
        return SignedBytes.lexicographicalComparator().compare(o1.key(), o2.key());
      }
    });
  }

  public List<Document> getDocuments() {
    return documents;
  }
}
