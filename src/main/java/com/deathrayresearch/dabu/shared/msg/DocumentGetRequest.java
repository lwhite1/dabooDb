package com.deathrayresearch.dabu.shared.msg;

import com.deathrayresearch.dabu.shared.Document;

/**
 *
 */
public class DocumentGetRequest extends AbstractRequest {

  private final byte[] key;

  public DocumentGetRequest(byte[] key) {
    super(RequestType.GET);
    this.key = key;
  }

  public byte[] getKey() {
    return key;
  }
}
