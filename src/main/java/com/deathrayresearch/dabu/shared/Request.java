package com.deathrayresearch.dabu.shared;

/**
 *
 */
public interface Request {
  RequestType getRequestType();

  byte[] getRequestId();
}
