package com.deathrayresearch.dabu.server;

import com.deathrayresearch.dabu.server.io.CompressionType;
import com.deathrayresearch.dabu.server.io.WriteLog;
import com.deathrayresearch.dabu.shared.Document;
import com.deathrayresearch.dabu.shared.msg.AbstractReply;
import com.deathrayresearch.dabu.shared.msg.DocDeleteRequest;
import com.deathrayresearch.dabu.shared.msg.GetReply;
import com.deathrayresearch.dabu.shared.msg.DocGetRequest;
import com.deathrayresearch.dabu.shared.msg.QueryRequest;
import com.deathrayresearch.dabu.shared.msg.DocWriteRequest;
import com.deathrayresearch.dabu.shared.msg.Reply;
import com.deathrayresearch.dabu.shared.msg.Request;

import java.io.IOException;

/**
 *
 */
public class DbServer {

  public static final DbServer INSTANCE = new DbServer();

  private final WriteLog writeLog;
  private final Db db;
  private final DbConfig config;

  private DbServer() {
    db = new MemoryDb();
    writeLog = WriteLog.getInstance();
    config = new DbConfig();
  }

  /**
   *
   */
  public Reply handleRequest(Request request) {
    switch (request.getRequestType()) {
      case WRITE:return handleRequest((DocWriteRequest) request);
      case GET:return handleRequest((DocGetRequest) request);
      case DELETE:return handleRequest((DocDeleteRequest) request);
      case QUERY:return handleRequest((QueryRequest) request);
      default: throw new RuntimeException("Unknown request type");
    }
  }

  private Reply handleRequest(DocWriteRequest request) {
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
   */
  private Reply handleRequest(DocDeleteRequest request) {
    return new AbstractReply(request);
  }

  /**
   */
  private Reply handleRequest(QueryRequest request) {
    return new AbstractReply(request);
  }

  /**
   */
  private Reply handleRequest(DocGetRequest request) {
    byte[] result = db.get(request.getKey());
    return new GetReply(request, result);
  }

  public DbConfig getConfig() {
    return config;
  }

  public Class getDocumentClass() {
    return config.getDocumentClass();
  }

  public CompressionType getDocumentContentCompressionType() {
    return config.getDocumentContentCompressionType();
  }

  public CompressionType getRequestCompressionType() {
    return config.getRequestCompressionType();
  }
}
