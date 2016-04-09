package com.deathrayresearch.dabu.shared;

import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;

/**
 * The interface to be implemented by any class to be persisted
 */
public interface DocumentContents {

  Gson GSON = new Gson();

  default String getType() {
    return this.getClass().getCanonicalName();
  }

  default byte[] marshall() {
    return GSON.toJson(this).getBytes(StandardCharsets.UTF_8);
  }

  byte[] getKey();
}
