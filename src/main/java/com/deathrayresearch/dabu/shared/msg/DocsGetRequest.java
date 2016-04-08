package com.deathrayresearch.dabu.shared.msg;

import java.util.Collection;

/**
 *
 */
public class DocsGetRequest extends AbstractRequest {

  private final Collection<byte[]> keys;

  public DocsGetRequest(Collection<byte[]> keys) {
    super(RequestType.GET);
    this.keys = keys;
  }

  public Collection<byte[]> getKey() {
    return keys;
  }
}
