package com.deathrayresearch.dabu.shared.msg;

import javax.annotation.concurrent.Immutable;

/**
 * A request to get a single document
 */
@Immutable
public final class DocGetRequest extends AbstractRequest {

  private final byte[] key;

  public DocGetRequest(byte[] key) {
    super(RequestType.DOCUMENT_GET);
    this.key = key;
  }

  public byte[] getKey() {
    return key;
  }
}
