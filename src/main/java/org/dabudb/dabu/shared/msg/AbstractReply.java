package org.dabudb.dabu.shared.msg;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * A reply to a request to perform a database action
 */
public class AbstractReply implements Reply {

  private final static String TIME_ZONE_FOR_TIMESTAMP = "Z"; // UTC

  private final ZonedDateTime timestamp;

  private final byte[] requestId;

  private final RequestType type;

  public AbstractReply(Request request) {
    timestamp = ZonedDateTime.now(ZoneId.of(TIME_ZONE_FOR_TIMESTAMP));
    requestId = request.getRequestId();
    type = request.getRequestType();
  }

  @Override
  public ZonedDateTime getTimestamp() {
    return timestamp;
  }

  @Override
  public byte[] getRequestId() {
    return requestId;
  }

  @Override
  public RequestType getRequestType() {
    return type;
  }
}
