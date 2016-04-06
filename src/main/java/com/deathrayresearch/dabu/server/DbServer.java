package com.deathrayresearch.dabu.server;

import com.deathrayresearch.dabu.server.io.RequestByteLog;
import com.deathrayresearch.dabu.shared.msg.AbstractReply;
import com.deathrayresearch.dabu.shared.msg.Reply;
import com.deathrayresearch.dabu.shared.msg.Request;

/**
 *
 */
public class DbServer {

  private final RequestByteLog writeLog = new RequestByteLog();

  public Reply handleRequest(Request request) {
    writeLog.logRequest(request);
    return new AbstractReply(request);
  }
}
