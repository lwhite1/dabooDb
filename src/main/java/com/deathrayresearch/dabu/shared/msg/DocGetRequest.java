package com.deathrayresearch.dabu.shared.msg;

import com.deathrayresearch.dabu.shared.Document;

/**
 *
 */
public class DocGetRequest extends AbstractRequest {

  private final byte[] key;

  public DocGetRequest(byte[] key) {
    super(RequestType.GET);
    this.key = key;
  }

  public byte[] getKey() {
    return key;
  }
}
