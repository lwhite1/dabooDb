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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The primary controller for the db. It receives input from a CommServer and forwards to a WAL (log) and db
 */
class DbServer {

  private static DbServer INSTANCE;

  public static DbServer get() {

    if (INSTANCE == null) {
      INSTANCE = new DbServer();
    }
    return INSTANCE;
  }

  private Db db() {
    return ServerSettings.getInstance().getDb();
  }

  private WriteAheadLog writeLog() {
    return ServerSettings.getInstance().getWriteAheadLog();
  }

  Request.WriteReply handleRequest(WriteRequest request, byte[] requestBytes) {
    try {
      writeLog().log(requestBytes);

      List<Request.DocumentKeyValue> documents = request.getBody().getDocumentKeyValueList();
      Map<byte[], byte[]> documentMap = new HashMap<>();
      for (Request.DocumentKeyValue keyValue : documents) {
        documentMap.put(keyValue.getKey().toByteArray(), keyValue.getValue().toByteArray());
      }
      db().write(documentMap);
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

  /**
   * Replay the writeAheadLog, on startup, to initialize the in-memory data structures
   *
   * TODO(lwhite): Review. This probably needs to lock the files or otherwise fully synchronize
   */
  public synchronized void replayWAL() {

    while (writeLog().hasNext()) {
      byte[] requestBytes = writeLog().next();

      try {
        WriteRequest request = WriteRequest.parseFrom(requestBytes);
        List<Request.DocumentKeyValue> documents = request.getBody().getDocumentKeyValueList();
        Map<byte[], byte[]> documentMap = new HashMap<>();
        for (Request.DocumentKeyValue keyValue : documents) {
          documentMap.put(keyValue.getKey().toByteArray(), keyValue.getValue().toByteArray());
        }
        db().write(documentMap);
      } catch (IOException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      }
    }
  }

  private Request.ErrorCondition noErrorCondition() {
    return Request.ErrorCondition.newBuilder()
        .setErrorType(Request.ErrorType.NONE)
        .setDescription("")
        .build();
  }
}
