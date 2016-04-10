package org.dabudb.dabu.shared.msg;

import org.dabudb.dabu.shared.msg.request.RequestType;

import java.time.ZonedDateTime;

/**
 *
 */
public interface Message {

  byte[] getRequestId();

  ZonedDateTime getTimestamp();

  RequestType getRequestType();
}
