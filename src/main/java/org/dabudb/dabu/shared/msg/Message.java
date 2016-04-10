package org.dabudb.dabu.shared.msg;

import org.dabudb.dabu.shared.msg.request.RequestType;

import java.time.ZonedDateTime;

/**
 *  A message (request or reply) between a database client and server
 */
public interface Message {

  /**
   * Returns a unique identifier for the request.
   * This must be unique even if it is created by a client and there is more than one client.
   */
  byte[] getRequestId();

  /**
   * Returns the time the Message was created (Note: NOT the time the message was sent)
   */
  ZonedDateTime getTimestamp();

  /**
   * Returns the type of the request (a write of one or more documents, for example)
   */
  RequestType getRequestType();
}
