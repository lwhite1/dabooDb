package com.deathrayresearch.dabu.shared.msg;

import java.time.ZonedDateTime;

/**
 *
 */
public interface Request {

  RequestType getRequestType();

  byte[] getRequestId();

  ZonedDateTime getTimestamp();
}
