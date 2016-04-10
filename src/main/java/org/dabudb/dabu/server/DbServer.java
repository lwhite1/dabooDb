package org.dabudb.dabu.server;

import org.dabudb.dabu.server.io.WriteAheadLog;
import org.dabudb.dabu.shared.Document;
import org.dabudb.dabu.shared.msg.reply.AbstractReply;
import org.dabudb.dabu.shared.msg.reply.DeleteReply;
import org.dabudb.dabu.shared.msg.request.DocDeleteRequest;
import org.dabudb.dabu.shared.msg.reply.DocGetReply;
import org.dabudb.dabu.shared.msg.request.DocGetRequest;
import org.dabudb.dabu.shared.msg.request.DocsDeleteRequest;
import org.dabudb.dabu.shared.msg.reply.DocsGetReply;
import org.dabudb.dabu.shared.msg.request.DocsGetRequest;
import org.dabudb.dabu.shared.msg.request.DocsWriteRequest;
import org.dabudb.dabu.shared.msg.request.QueryRequest;
import org.dabudb.dabu.shared.msg.request.DocWriteRequest;
import org.dabudb.dabu.shared.msg.reply.Reply;
import org.dabudb.dabu.shared.msg.request.Request;
import org.dabudb.dabu.shared.msg.reply.WriteReply;

import java.io.IOException;
import java.util.List;

/**
 *
 */
public class DbServer {

  private static DbServer INSTANCE;

  public static DbServer get() {

    if (INSTANCE == null) {
      INSTANCE = new DbServer();
    }
    return INSTANCE;
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
      writeLog().logRequest(request);
      db().write(request.getKey(), request.getDocumentBytes());
     } catch (IOException e) {
      e.printStackTrace();
      throw(new RuntimeException(e));
    }
    return new WriteReply(request);
  }

  private Db db() {
    return Settings.getInstance().getDb();
  }

  private WriteAheadLog writeLog() {
    return Settings.getInstance().getWriteAheadLog();
  }

  private WriteReply handleRequest(DocsWriteRequest request) {
    try {
      writeLog().logRequest(request);
      List<Document> documents = request.getDocuments();
      db().write(documents);
     } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return new WriteReply(request);
  }

  /**
   */
  private DeleteReply handleRequest(DocDeleteRequest request) {
    try {
      writeLog().logRequest(request);
      byte[] key = request.getKey();
      db().delete(key);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return new DeleteReply(request);
  }

  /**
   */
  private DeleteReply handleRequest(DocsDeleteRequest request) {
    try {
      writeLog().logRequest(request);
      db().delete(request.getKeys());
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
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
    byte[] result = db().get(request.getKey());
    return new DocGetReply(request, result);
  }

  private DocsGetReply handleRequest(DocsGetRequest request) {
    List<byte[]> result = db().get(request.getKeys());
    return new DocsGetReply(request, result);
  }
}
