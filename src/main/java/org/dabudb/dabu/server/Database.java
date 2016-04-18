package org.dabudb.dabu.server;

import com.google.protobuf.ByteString;
import org.dabudb.dabu.server.db.Db;
import org.dabudb.dabu.server.io.WriteAheadLog;

import org.dabudb.dabu.generated.protobufs.Request;
import org.dabudb.dabu.generated.protobufs.Request.GetReply;
import org.dabudb.dabu.generated.protobufs.Request.WriteReply;
import org.dabudb.dabu.generated.protobufs.Request.WriteRequest;
import org.dabudb.dabu.shared.exceptions.RuntimePersistenceException;
import org.dabudb.dabu.shared.exceptions.RuntimeRequestException;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The primary controller for the db. It receives input from a CommServer and forwards to a WAL (log) and db
 */
class Database implements DatabaseAdmin {

  private static Database INSTANCE;

  // The primary storage mechanism. Used to answer requests for data
  private Db db;

  // The Write request log. It ensures that updates are persistent
  private WriteAheadLog writeAheadLog;

  /**
   * Returns an instance of this class
   */
  public static Database get() {

    synchronized (Database.class) {
      if (INSTANCE == null) {
        INSTANCE = new Database();
      }
    }
    return INSTANCE;
  }

  /**
   * Private default constructor to prevent creation of new instances by outsiders
   */
  private Database() {}


  /**
   * Handles a request to write data to the database and returns an appropriate reply
   */
  Request.WriteReply handleRequest(WriteRequest request, byte[] requestBytes) {
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
      String msg = "An IOException was caught handling a WRITE request";
      throw new RuntimeRequestException(msg, e, request.getHeader().getId().toByteArray());
    } catch (Throwable e) {
      // We catch everything here to make sure it is logged before exiting
      String msg = "A Throwable was caught handling a WRITE request";
      throw new RuntimeRequestException(msg, e, request.getHeader().getId().toByteArray());
    }

    return getWriteReply(request);
  }

  /**
   * Handles a request to get data from the database and returns an appropriate reply
   *
   * @throws RuntimeRequestException if a runtime exception occurred while fulfilling this request
   */
   Request.GetReply handleRequest(Request.GetRequest request) {
    List<ByteString> result = null;
    Request.ErrorCondition condition = Request.ErrorCondition.getDefaultInstance();
    try {
      result = db().get(request.getBody().getKeyList());
    } catch (Throwable throwable) {
      // We catch everything here to make sure it is logged before exiting
      String msg = "A Throwable was caught handling a GET request";
      throw new RuntimeRequestException(msg, throwable, request.getHeader().getId().toByteArray());
    }
    return getGetReply(request, result, condition);
  }

  /**
   * Returns the number of documents in the database
   */
  int size() {
    return db().size();
  }

  /**
   * Returns true if there is no data in this database; false otherwise
   */
  boolean isEmpty() {
    return size() == 0;
  }

  /**
   * Exports the database in its entirety to the given file
   */
  public void export(File file) {
    db().exportDocuments(file);
  }

  /**
   * Backs-up the database to a file, and clears the WriteAheadLog, so that going forward it will only contain
   * updates since the latest backup
   * TODO(lwhite): Need more sophisticated approach to backup and recovery that addresses issues of concurrent writes
   */
  public void backup(File file) {
    //TODO(lwhite): Make sure that we delete the file if it already exists, rather than append to it.
    db().exportDocuments(file);
    try {
      writeLog().clear();
    } catch (IOException e) {
      e.printStackTrace();
      String message = "Unable to complete backup operation because of an IOException clearing the WAL";
      throw new RuntimePersistenceException(message, e);
    }
  }

  /**
   * Loads into this database service the data in the given backup file
   */
  public void recoverFromBackup(File backupFile) {

    // get everything from the last backup, if any
    db().importDocuments(backupFile);

    // now get anything that was written to the WAL since the last backup.
    loadExistingDataFromWAL();

    // now clear the WAL
    try {
      writeLog().clear();
    } catch (IOException e) {
      e.printStackTrace();
      String message = "Unable to complete recovery from backup because of an IOException clearing the WAL";
      throw new RuntimePersistenceException(message, e);
    }
  }

  /**
   * Clears both the primary data structure and the WAL
   */
  public void clear() {
    db().clear();
    try {
      writeLog().clear();
    } catch (IOException e) {
      String message = "Unable to complete clearing the database, because of an IOException clearing the WAL";
      e.printStackTrace();
      throw new RuntimePersistenceException(message, e);
    }
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

  /**
   * Replay the writeAheadLog, on startup, to initialize the in-memory data structures
   * <p>
   * TODO(lwhite): Review. This probably needs to lock the files or otherwise fully synchronize
   */
  private synchronized void loadExistingDataFromWAL() {

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
        String message = "Unable to complete loading existing data from the WAL due to an IOException";
        throw new RuntimePersistenceException(message, e);
      }
    }
  }

  private GetReply getGetReply(Request.GetRequest request, List<ByteString> result, Request.ErrorCondition condition) {
    return GetReply.newBuilder()
        .setRequestId(request.getHeader().getId())
        .setTimestamp(Instant.now().toEpochMilli())
        .addAllDocumentBytes(result)
        .setErrorCondition(condition)
        .build();
  }

  private Request.ErrorCondition getErrorCondition(Request.GetRequest request, String msg, RuntimeRequestException
      rtReqEx) {
    Request.ErrorCondition condition;
    condition = Request.ErrorCondition.newBuilder()
        .setDescription(msg)
        .setErrorType(Request.ErrorType.NONE)
        .setRequestId(request.getHeader().getId())
        .setToken(ByteString.copyFrom(rtReqEx.getToken()))
        .setDescription(rtReqEx.getMessage())
        .setOrigin(Request.ErrorOrigin.SERVER)
        .build();
    return condition;
  }

  private WriteReply getWriteReply(WriteRequest request) {
    return WriteReply.newBuilder()
        .setRequestId(request.getHeader().getId())
        .setTimestamp(Instant.now().toEpochMilli())
        .build();
  }
}
