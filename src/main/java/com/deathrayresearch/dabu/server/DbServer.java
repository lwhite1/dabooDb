package com.deathrayresearch.dabu.server;

import com.deathrayresearch.dabu.server.io.WriteLog;
import com.deathrayresearch.dabu.shared.Document;
import com.deathrayresearch.dabu.shared.DocumentContents;
import com.deathrayresearch.dabu.shared.StandardDocument;
import com.deathrayresearch.dabu.shared.msg.AbstractReply;
import com.deathrayresearch.dabu.shared.msg.GetReply;
import com.deathrayresearch.dabu.shared.msg.GetRequest;
import com.deathrayresearch.dabu.shared.msg.WriteRequest;
import com.deathrayresearch.dabu.shared.msg.Reply;
import com.deathrayresearch.dabu.shared.msg.Request;

import java.io.IOException;

/**
 *
 */
public class DbServer {

  private final WriteLog writeLog;
  private final Db db;

  public DbServer() {
    db = new MemoryDb();
    writeLog = WriteLog.getInstance();
  }

  public Reply handleRequest(WriteRequest request) {
    try {
      writeLog.logRequest(request);
      Document document = request.getDocument();
      db.write(document.key(), document.marshall());
    } catch (IOException e) {
      e.printStackTrace();
      //TODO(lwhite): We should probably exit if we can't write to the WAL
    }
    return new AbstractReply(request);
  }

  /**
   * TODO(lwhite) replace with versions for specialized request types
   */
  public Reply handleRequest(Request request) {
    return new AbstractReply(request);
  }

  /**
   */
  public Reply handleRequest(GetRequest request) {
    byte[] result = db.get(request.getKey());
    return new GetReply(request, new StandardDocument(new DocumentContents() {
      @Override
      public String getType() {
        return null;
      }

      @Override
      public byte[] getKey() {
        return new byte[0];
      }
    }));
  }
}
