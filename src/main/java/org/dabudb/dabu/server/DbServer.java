package org.dabudb.dabu.server;

import com.google.common.base.Stopwatch;
import com.google.protobuf.ByteString;
import org.dabudb.dabu.server.db.Db;
import org.dabudb.dabu.server.io.WriteAheadLog;

import org.dabudb.dabu.generated.protobufs.Request;
import org.dabudb.dabu.generated.protobufs.Request.GetReply;
import org.dabudb.dabu.generated.protobufs.Request.WriteReply;
import org.dabudb.dabu.generated.protobufs.Request.WriteRequest;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * The primary controller for the db. It receives input from a CommServer and forwards to a WAL (log) and db
 */
public class DbServer {

  private static DbServer INSTANCE;

  private Db db;
  private WriteAheadLog writeAheadLog;

  public static DbServer get() {

    synchronized (DbServer.class) {
      if (INSTANCE == null) {
        INSTANCE = new DbServer();
      }
    }
    return INSTANCE;
  }

  private DbServer() {
    //loadExistingDataFromWAL();
  }

  /**
   * Initialize fields with values that are determined by ServerSettings
   */
  private void init() {
    ServerSettings serverSettings = ServerSettings.getInstance();
    db = serverSettings.getDb();
    writeAheadLog = serverSettings.getWriteAheadLog();
  }

  private Db db() {
    if (db == null) {
      init();
    }
    return db;
  }

  private WriteAheadLog writeLog() {
    if (writeAheadLog == null) {
      init();
    }
    return writeAheadLog;
  }

  public Request.WriteReply handleRequest(WriteRequest request, byte[] requestBytes) {
    try {
      writeLog().log(requestBytes);
      if (!request.getIsDelete()) {
        List<Request.DocumentKeyValue> documents = request.getWriteBody().getDocumentKeyValueList();
        Map<byte[], byte[]> documentMap = new HashMap<>();
        for (Request.DocumentKeyValue keyValue : documents) {
          documentMap.put(keyValue.getKey().toByteArray(), keyValue.getValue().toByteArray());
        }
        db().write(documentMap);
      } else {
        List<Request.Document> documents = request.getDeleteBody().getDocumentList();
        db().delete(documents);
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }

    return WriteReply.newBuilder()
        .setRequestId(request.getHeader().getId())
        .setTimestamp(Instant.now().toEpochMilli())
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
   * <p>
   * TODO(lwhite): Review. This probably needs to lock the files or otherwise fully synchronize
   */
  private synchronized void loadExistingDataFromWAL() {

    int count = 0;
    while (writeLog().hasNext()) {

      byte[] requestBytes = writeLog().next();

      try {
        WriteRequest request = WriteRequest.parseFrom(requestBytes);
        if (!request.getIsDelete()) {
          List<Request.DocumentKeyValue> documents = request.getWriteBody().getDocumentKeyValueList();
          Map<byte[], byte[]> documentMap = new HashMap<>();
          for (Request.DocumentKeyValue keyValue : documents) {
            documentMap.put(keyValue.getKey().toByteArray(), keyValue.getValue().toByteArray());
          }
          db().write(documentMap);
        } else {
          List<Request.Document> documents = request.getDeleteBody().getDocumentList();
          Map<byte[], byte[]> documentMap = new HashMap<>();
          for (Request.Document document : documents) {
            documentMap.put(document.getKey().toByteArray(), document.toByteArray());
          }
          db().write(documentMap);
        }
      } catch (IOException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
      }
      count++;
      if (count % 100_000 == 0) {
        System.out.println("Loaded " + count + " documents ");
      }
    }
    System.out.println("Loaded " + count + " documents ");
  }

  /**
   * Returns the number of documents in the database
   */
  public int size() {
    return db().size();
  }

  public boolean isEmpty() {
    return size() == 0;
  }

  /**
   * Exports the database in its entirety to the given file
   */
  public void export(File file) {
    db().exportDocuments(file);
  }

  /*
   * TODO(lwhite): Need more sophisticated approach to backup and recovery that addresses issues of concurrent writes
   */
  /**
   * Backs-up the database to a file, and clears the WriteAheadLog, so that going forward it will only contain
   * updates since the latest backup
   */
  public void backup(File file) {
    //TODO(lwhite): Make sure that we delete the file if it already exists, rather than append to it.
    db().exportDocuments(file);
    try {
      writeLog().clear();
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  public void recoverFromBackup(File file) {

    // get everything from the last backup, if any
    db().importDocuments(file);

    // now get anything that was written to the WAL since the last backup.
    loadExistingDataFromWAL();

    // now clear the WAL
    try {
      writeLog().clear();
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  public void clear() {
    db().clear();
    try {
      writeLog().clear();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
