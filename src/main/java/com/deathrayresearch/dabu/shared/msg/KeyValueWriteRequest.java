package com.deathrayresearch.dabu.shared.msg;

/**
 *
 */
public class KeyValueWriteRequest extends AbstractRequest {

  private final byte[] key;
  private final byte[] value;

  public KeyValueWriteRequest(byte[] key, byte[] value) {
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
