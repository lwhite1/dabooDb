package com.deathrayresearch.dabu.shared.msg;

import com.deathrayresearch.dabu.shared.Document;

/**
 *
 */
public class GetRequest extends AbstractRequest {

  private final byte[] key;

  public GetRequest(byte[] key) {
    super(RequestType.GET);
    this.key = key;
  }

  public byte[] getKey() {
    return key;
  }
}
