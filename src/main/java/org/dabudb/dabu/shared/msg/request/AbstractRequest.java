package org.dabudb.dabu.shared.msg.request;

import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Objects;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AbstractRequest that = (AbstractRequest) o;
    return requestType == that.requestType &&
        Objects.equals(timestamp, that.timestamp) &&
        Arrays.equals(requestId, that.requestId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(requestType, timestamp, requestId);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("AbstractRequest{");
    sb.append("requestType=").append(requestType);
    sb.append(", timestamp=").append(timestamp);
    sb.append(", requestId=").append(Arrays.toString(requestId));
    sb.append('}');
    return sb.toString();
  }
}
