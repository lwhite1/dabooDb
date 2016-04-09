package com.deathrayresearch.dabu.shared.msg;

import java.util.Collection;
import java.util.List;

/**
 *
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
