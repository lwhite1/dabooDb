package com.deathrayresearch.dabu.shared.msg;

/**
 *
 */
public class DeleteRequest extends AbstractRequest {

  private final byte[] key;

  public DeleteRequest(byte[] key) {
    super(RequestType.DELETE);
    this.key = key;
  }

  public byte[] getKey() {
    return key;
  }

}
