package com.deathrayresearch.dabu.shared;

import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;

/**
 *
 */
public interface DocumentContents {

  static final Gson GSON = new Gson();

  String getType();

  default byte[] marshall() {
    return GSON.toJson(this).getBytes(StandardCharsets.UTF_8);
  }

  byte[] getKey();
}
