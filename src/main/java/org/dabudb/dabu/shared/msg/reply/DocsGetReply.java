package org.dabudb.dabu.shared.msg.reply;

import org.dabudb.dabu.shared.msg.request.Request;

import javax.annotation.concurrent.Immutable;
import java.util.List;

/**
 * A reply to a request to get a list of documents according to their keys.
 * It contains the documents matching all the keys that it could find.
 */
@Immutable
public final class DocsGetReply extends AbstractReply {

  private final List<byte[]> documents;

  public DocsGetReply(Request request, List<byte[]> documents) {
    super(request);
    this.documents = documents;
  }

  public List<byte[]> getDocuments() {
    return documents;
  }
}
