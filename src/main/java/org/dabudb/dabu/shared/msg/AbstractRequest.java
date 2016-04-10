package org.dabudb.dabu.shared.msg;

import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * A request to execute a database action
 */
public abstract class AbstractRequest implements Request {

  private final static String TIME_ZONE_FOR_TIMESTAMP = "Z"; // UTC

  private final RequestType requestType;

  private final ZonedDateTime timestamp;

  private final byte[] requestId;

  AbstractRequest(RequestType requestType) {
    this.requestType = requestType;
    requestId = UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8);
    timestamp = ZonedDateTime.now(ZoneId.of(TIME_ZONE_FOR_TIMESTAMP));
  }

  @Override
  public RequestType getRequestType() {
    return requestType;
  }

  @Override
  public byte[] getRequestId() {
    return requestId;
  }

  @Override
  public ZonedDateTime getTimestamp() {
    return timestamp;
  }
}
