package org.dabudb.dabu.shared.msg;

import java.util.List;

/**
 *  A request to get a collection of documents using their document keys
 */
public class DocsGetRequest extends AbstractRequest {

  private final List<byte[]> keys;


  public DocsGetRequest(List<byte[]> keys) {
    super(RequestType.DOCUMENT_GET);
    this.keys = keys;
  }

  public List<byte[]> getKeys() {
    return keys;
  }
}
