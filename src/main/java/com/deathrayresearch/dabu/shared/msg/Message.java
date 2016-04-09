package com.deathrayresearch.dabu.shared.msg;

import java.time.ZonedDateTime;

/**
 *
 */
public interface Message {

  byte[] getRequestId();

  ZonedDateTime getTimestamp();

  RequestType getRequestType();
}
