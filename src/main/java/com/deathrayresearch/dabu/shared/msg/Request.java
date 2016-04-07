package com.deathrayresearch.dabu.shared.msg;

import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;

/**
 *
 */
public interface Request {

  static Gson GSON = new Gson();

  RequestType getRequestType();

  byte[] getRequestId();

  ZonedDateTime getTimestamp();

  default byte[] marshall() {
    return GSON.toJson(this).getBytes(StandardCharsets.UTF_8);
  }
}
