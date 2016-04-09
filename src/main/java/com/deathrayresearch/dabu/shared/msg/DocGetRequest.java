package com.deathrayresearch.dabu.shared.msg;

/**
 *
 */
public class DocGetRequest extends AbstractRequest {

  private final byte[] key;

  public DocGetRequest(byte[] key) {
    super(RequestType.DOCUMENT_GET);
    this.key = key;
  }

  public byte[] getKey() {
    return key;
  }
}
