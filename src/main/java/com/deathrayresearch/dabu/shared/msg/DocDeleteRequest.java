package com.deathrayresearch.dabu.shared.msg;

import javax.annotation.concurrent.Immutable;

/**
 * A request to delete a single document
 */
@Immutable
public final class DocDeleteRequest extends AbstractRequest {

  private final byte[] key;

  public DocDeleteRequest(byte[] key) {
    super(RequestType.DOCUMENT_DELETE);
    this.key = key;
  }

  public byte[] getKey() {
    return key;
  }

}
