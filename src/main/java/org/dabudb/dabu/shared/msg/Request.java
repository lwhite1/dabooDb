package org.dabudb.dabu.shared.msg;

import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;

/**
 *
 */
public interface Request extends Message {

  static Gson GSON = new Gson();

  default byte[] marshall() {
    return GSON.toJson(this).getBytes(StandardCharsets.UTF_8);
  }
}
