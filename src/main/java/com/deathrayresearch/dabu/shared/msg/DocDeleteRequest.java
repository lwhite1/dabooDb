package com.deathrayresearch.dabu.shared.msg;

/**
 * A request to delete a single document
 */
public class DocDeleteRequest extends AbstractRequest {

  private final byte[] key;

  public DocDeleteRequest(byte[] key) {
    super(RequestType.DOCUMENT_DELETE);
    this.key = key;
  }

  public byte[] getKey() {
    return key;
  }

}
