package com.deathrayresearch.dabu.server;

import com.deathrayresearch.dabu.shared.compression.CompressionType;
import com.deathrayresearch.dabu.server.io.WriteLog;
import com.deathrayresearch.dabu.shared.Document;
import com.deathrayresearch.dabu.shared.msg.AbstractReply;
import com.deathrayresearch.dabu.shared.msg.DeleteReply;
import com.deathrayresearch.dabu.shared.msg.DocDeleteRequest;
import com.deathrayresearch.dabu.shared.msg.DocGetReply;
import com.deathrayresearch.dabu.shared.msg.DocGetRequest;
import com.deathrayresearch.dabu.shared.msg.DocsDeleteRequest;
import com.deathrayresearch.dabu.shared.msg.DocsGetReply;
import com.deathrayresearch.dabu.shared.msg.DocsGetRequest;
import com.deathrayresearch.dabu.shared.msg.DocsWriteRequest;
import com.deathrayresearch.dabu.shared.msg.QueryRequest;
import com.deathrayresearch.dabu.shared.msg.DocWriteRequest;
import com.deathrayresearch.dabu.shared.msg.Reply;
import com.deathrayresearch.dabu.shared.msg.Request;
import com.deathrayresearch.dabu.shared.msg.WriteReply;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

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
      case DOCUMENT_WRITE:return handleRequest((DocWriteRequest) request);
      case DOCUMENTS_WRITE:return handleRequest((DocsWriteRequest) request);
      case DOCUMENT_GET:return handleRequest((DocGetRequest) request);
      case DOCUMENTS_GET:return handleRequest((DocsGetRequest) request);
      case DOCUMENT_DELETE:return handleRequest((DocDeleteRequest) request);
      case DOCUMENTS_DELETE:return handleRequest((DocsDeleteRequest) request);
      case QUERY:return handleRequest((QueryRequest) request);
      default: throw new RuntimeException("Unknown request type");
    }
  }

  private WriteReply handleRequest(DocWriteRequest request) {
    try {
      writeLog.logRequest(request);
      Document document = request.getDocument();
      db.write(document.key(), document.marshall());
     } catch (IOException e) {
      e.printStackTrace();
      //TODO(lwhite): We should probably exit if we can't write to the WAL
    }
    return new WriteReply(request);
  }

  private WriteReply handleRequest(DocsWriteRequest request) {
    try {
      writeLog.logRequest(request);
      List<Document> documents = request.getDocuments();
      db.write(documents);
     } catch (IOException e) {
      e.printStackTrace();
      //TODO(lwhite): We should probably exit if we can't write to the WAL
    }
    return new WriteReply(request);
  }

  /**
   */
  private DeleteReply handleRequest(DocDeleteRequest request) {
    return new DeleteReply(request);
  }

  /**
   */
  private DeleteReply handleRequest(DocsDeleteRequest request) {
    return new DeleteReply(request);
  }

  /**
   */
  private Reply handleRequest(QueryRequest request) {
    return new AbstractReply(request);
  }

  /**
   */
  private DocGetReply handleRequest(DocGetRequest request) {
    byte[] result = db.get(request.getKey());
    return new DocGetReply(request, result);
  }

  private DocsGetReply handleRequest(DocsGetRequest request) {
    List<byte[]> result = db.get(request.getKeys());
    return new DocsGetReply(request, result);
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
