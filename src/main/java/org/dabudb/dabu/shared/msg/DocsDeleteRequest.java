package org.dabudb.dabu.shared.msg;

import javax.annotation.concurrent.Immutable;
import java.util.Collection;
import java.util.List;

/**
 * A request to delete a collection of documents
 */
@Immutable
public final class DocsDeleteRequest extends AbstractRequest {

  private final List<byte[]> keys;

  public DocsDeleteRequest(List<byte[]> keys) {
    super(RequestType.DOCUMENTS_DELETE);
    this.keys = keys;
  }

  public List<byte[]> getKeys() {
    return keys;
  }

}
