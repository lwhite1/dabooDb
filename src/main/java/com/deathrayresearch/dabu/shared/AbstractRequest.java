package com.deathrayresearch.dabu.shared;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * A request to execute a database action
 */
public class AbstractRequest implements Request {

  private final RequestType requestType;

  private final byte[] requestId;

  public static Request create(RequestType requestType) {
    return new AbstractRequest(requestType);
  }

  AbstractRequest(RequestType requestType) {
    this.requestType = requestType;
    requestId = UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8);
  }

  @Override
  public RequestType getRequestType() {
    return requestType;
  }

  @Override
  public byte[] getRequestId() {
    return requestId;
  }
}
