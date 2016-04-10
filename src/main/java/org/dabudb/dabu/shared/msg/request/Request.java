package org.dabudb.dabu.shared.msg.request;

import com.google.gson.Gson;
import org.dabudb.dabu.shared.msg.Message;

import java.nio.charset.StandardCharsets;

/**
 *
 */
public interface Request extends Message {

  static Gson GSON = new Gson();

  default byte[] marshall() {
    return GSON.toJson(this).getBytes(StandardCharsets.UTF_8);
  }

  /**
   * Returns the type of the request (a write of one or more documents, for example)
   */
  RequestType getRequestType();

}
