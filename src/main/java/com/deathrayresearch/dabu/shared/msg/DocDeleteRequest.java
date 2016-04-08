package com.deathrayresearch.dabu.shared.msg;

/**
 *
 */
public class DocDeleteRequest extends AbstractRequest {

  private final byte[] key;

  public DocDeleteRequest(byte[] key) {
    super(RequestType.DELETE);
    this.key = key;
  }

  public byte[] getKey() {
    return key;
  }

}
