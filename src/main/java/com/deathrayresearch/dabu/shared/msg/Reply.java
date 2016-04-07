package com.deathrayresearch.dabu.shared.msg;

import java.time.ZonedDateTime;

/**
 *
 */
public interface Reply {

  ZonedDateTime getTimestamp();

  byte[] getRequestId();

  RequestType getType();
}
