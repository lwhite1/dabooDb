package com.deathrayresearch.dabu.server;

import com.deathrayresearch.dabu.server.io.WriteLog;
import com.deathrayresearch.dabu.shared.msg.AbstractReply;
import com.deathrayresearch.dabu.shared.msg.Reply;
import com.deathrayresearch.dabu.shared.msg.Request;

import java.io.IOException;

/**
 *
 */
public class DbServer {

  private final WriteLog writeLog;

  public DbServer() {
    writeLog = WriteLog.getInstance();
  }

  public Reply handleRequest(Request request) {
    try {
      writeLog.logRequest(request);
    } catch (IOException e) {
      e.printStackTrace();
      //TODO(lwhite): We should probably exit if we can't write to the WAL
    }
    return new AbstractReply(request);
  }
}
