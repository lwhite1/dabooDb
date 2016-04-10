package org.dabudb.dabu.server;

import org.dabudb.dabu.server.io.WriteAheadLog;
import org.dabudb.dabu.shared.Document;
import org.dabudb.dabu.shared.msg.AbstractReply;
import org.dabudb.dabu.shared.msg.DeleteReply;
import org.dabudb.dabu.shared.msg.DocDeleteRequest;
import org.dabudb.dabu.shared.msg.DocGetReply;
import org.dabudb.dabu.shared.msg.DocGetRequest;
import org.dabudb.dabu.shared.msg.DocsDeleteRequest;
import org.dabudb.dabu.shared.msg.DocsGetReply;
import org.dabudb.dabu.shared.msg.DocsGetRequest;
import org.dabudb.dabu.shared.msg.DocsWriteRequest;
import org.dabudb.dabu.shared.msg.QueryRequest;
import org.dabudb.dabu.shared.msg.DocWriteRequest;
import org.dabudb.dabu.shared.msg.Reply;
import org.dabudb.dabu.shared.msg.Request;
import org.dabudb.dabu.shared.msg.WriteReply;

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
    byte[] result = db().get(request.getKey());
    return new DocGetReply(request, result);
  }

  private DocsGetReply handleRequest(DocsGetRequest request) {
    List<byte[]> result = db().get(request.getKeys());
    return new DocsGetReply(request, result);
  }
}
