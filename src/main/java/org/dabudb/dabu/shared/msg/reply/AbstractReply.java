package org.dabudb.dabu.shared.msg.reply;

import org.dabudb.dabu.shared.msg.request.Request;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Objects;

/**
 * A reply to a request to perform a database action
 */
public abstract class AbstractReply implements Reply {

  private final static String TIME_ZONE_FOR_TIMESTAMP = "Z"; // UTC

  private final ZonedDateTime timestamp;

  private final byte[] requestId;

  private final ErrorCondition errorCondition;

  public AbstractReply(Request request) {
    timestamp = ZonedDateTime.now(ZoneId.of(TIME_ZONE_FOR_TIMESTAMP));
    requestId = request.getRequestId();
    errorCondition = null;
  }

  public AbstractReply(Request request, ErrorCondition errorCondition) {
    timestamp = ZonedDateTime.now(ZoneId.of(TIME_ZONE_FOR_TIMESTAMP));
    requestId = request.getRequestId();
    this.errorCondition = errorCondition;
  }

  @Override
  public ZonedDateTime getTimestamp() {
    return timestamp;
  }

  @Override
  public byte[] getRequestId() {
    return requestId;
  }

  public ErrorCondition getErrorCondition() {
    return errorCondition;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AbstractReply that = (AbstractReply) o;
    return Objects.equals(getTimestamp(), that.getTimestamp()) &&
        Arrays.equals(getRequestId(), that.getRequestId()) &&
        Objects.equals(getErrorCondition(), that.getErrorCondition());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getTimestamp(), getRequestId(), getErrorCondition());
  }

  @Override
  public String toString() {
    return "AbstractReply{" +
        "timestamp=" + timestamp +
        ", requestId=" + Arrays.toString(requestId) +
        ", errorCondition=" + errorCondition +
        '}';
  }
}
