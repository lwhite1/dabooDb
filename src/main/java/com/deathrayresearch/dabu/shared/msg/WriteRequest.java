package com.deathrayresearch.dabu.shared.msg;

/**
 *
 */
public class WriteRequest extends AbstractRequest {

  private final byte[] key;
  private final byte[] value;

  public WriteRequest(byte[] key, byte[] value) {
    super(RequestType.WRITE);
    this.key = key;
    this.value = value;
  }

  public byte[] getValue() {
    return value;
  }

  public byte[] getKey() {
    return key;
  }
}
