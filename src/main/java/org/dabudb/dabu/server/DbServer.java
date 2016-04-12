package org.dabudb.dabu.server;

import com.google.protobuf.ByteString;
import org.dabudb.dabu.server.db.Db;
import org.dabudb.dabu.server.io.WriteAheadLog;

import org.dabudb.dabu.shared.protobufs.Request;
import org.dabudb.dabu.shared.protobufs.Request.DeleteReply;
import org.dabudb.dabu.shared.protobufs.Request.GetReply;
import org.dabudb.dabu.shared.protobufs.Request.WriteReply;
import org.dabudb.dabu.shared.protobufs.Request.WriteRequest;

import java.io.IOException;
import java.time.Instant;
import java.util.List;

/**
 * The primary controller for the db. It receives input from a CommServer and forwards to a WAL (log) and db
 */
public class DbServer {

  private static DbServer INSTANCE;

  public static DbServer get() {

    if (INSTANCE == null) {
      INSTANCE = new DbServer();
    }
    return INSTANCE;
  }

  private Db db() {
    return Settings.getInstance().getDb();
  }

  private WriteAheadLog writeLog() {
    return Settings.getInstance().getWriteAheadLog();
  }

  public Request.WriteReply handleRequest(WriteRequest request) {
    try {
      writeLog().logRequest(request);
      List<Request.Document> documents = request.getBody().getDocumentList();
      db().write(documents);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }

    return WriteReply.newBuilder()
        .setRequestId(request.getHeader().getId())
        .setTimestamp(Instant.now().toEpochMilli())
        .build();
  }

  /**
   */
  public DeleteReply handleRequest(Request.DeleteRequest request) {
    try {
      writeLog().logRequest(request);
      List<Request.Document> documentList = request.getBody().getDocumentList();
      db().delete(documentList);
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return Request.DeleteReply.newBuilder()
        .setRequestId(request.getHeader().getId())
        .setTimestamp(Instant.now().toEpochMilli())
        .setErrorCondition(noErrorCondition())
        .build();
  }

  public Request.GetReply handleRequest(Request.GetRequest request) {
    List<ByteString> result = db().get(request.getBody().getKeyList());
    return GetReply.newBuilder()
        .setRequestId(request.getHeader().getId())
        .setTimestamp(Instant.now().toEpochMilli())
        .addAllDocumentBytes(result)
        .build();
  }

  private Request.ErrorCondition noErrorCondition() {
    return Request.ErrorCondition.newBuilder()
        .setErrorType(Request.ErrorType.NONE)
        .setDescription("")
        .build();
  }
}
