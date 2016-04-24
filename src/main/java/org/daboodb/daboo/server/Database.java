package org.daboodb.daboo.server;

import com.google.common.collect.Lists;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import org.daboodb.daboo.client.exceptions.RuntimeSerializationException;
import org.daboodb.daboo.generated.protobufs.Request.ErrorCondition;
import org.daboodb.daboo.generated.protobufs.Request.GetRangeRequest;
import org.daboodb.daboo.generated.protobufs.Request.GetRequest;
import org.daboodb.daboo.server.db.Db;
import org.daboodb.daboo.server.io.WriteAheadLog;

import org.daboodb.daboo.generated.protobufs.Request;
import org.daboodb.daboo.generated.protobufs.Request.GetReply;
import org.daboodb.daboo.generated.protobufs.Request.WriteReply;
import org.daboodb.daboo.generated.protobufs.Request.WriteRequest;
import org.daboodb.daboo.shared.exceptions.OptimisticLockException;
import org.daboodb.daboo.shared.exceptions.RuntimePersistenceException;
import org.daboodb.daboo.shared.exceptions.RuntimeRequestException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * The primary controller for the db. It receives input from a CommServer and forwards to a WAL (log) and db
 */
class Database implements DatabaseAdmin {

  private static Database INSTANCE;

  // The primary storage mechanism. Used to answer requests for data
  private Db db;

  // The Write request log. It ensures that updates are persistent
  private WriteAheadLog writeAheadLog;

  // true if we are notdoing any locking in this db. This would only be useful for single-threaded, embedded applicaions
  private boolean noLocking = false;

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

    ErrorCondition errorCondition = getNoErrorConditionForWrite(request);
    try {
      // we write the WAL
      //TODO(lwhite) Needs to be transactional, so we can safely recover from failure, & rollback if the write fails
      writeLog().log(requestBytes);

      checkLock(request);

      // we update the main data store
      //TODO(lwhite): We need to collect undo info so we can perform a roll-back on the datastore if needed

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
    } catch (OptimisticLockException e) {
      errorCondition = getOptimisticLockErrorCondition(request, e);
    }
    catch (Throwable e) {
      // We catch everything here to make sure it is logged before exiting
      String msg = "A Throwable was caught handling a WRITE request";
      throw new RuntimeRequestException(msg, e, request.getHeader().getId().toByteArray());
    }

    return getWriteReply(request, errorCondition);
  }

  private void checkLock(WriteRequest request) throws OptimisticLockException {
    if (noLocking) {
      return;
    }
    checkOptimisticLock(request);
  }

  private void checkOptimisticLock(WriteRequest request) throws OptimisticLockException {

    List<Request.DocumentKeyValue> docKeysAndValues = request.getWriteBody().getDocumentKeyValueList();
    Map<ByteString, Request.Document> keyValueMap = new HashMap<>();
    try { // make a list of keys to get
      for (Request.DocumentKeyValue keyValue : docKeysAndValues) {
        Request.Document document = Request.Document.parseFrom(keyValue.getValue());
        if (document.getInstanceVersion() != 0) {
          keyValueMap.put(keyValue.getKey(), document);
        }
      }

      // If we have no docs being updated, lets get out of here
      if (keyValueMap.isEmpty()) {
        return;
      }

      // get the prior version of any docs being updated
      List<ByteString> docByteStrings = db().get(Lists.newArrayList(keyValueMap.keySet()));

      // now check each doc to see if it's stale
      Request.Document prior;
      Request.Document current;
      for (ByteString string : docByteStrings) {
        prior = Request.Document.parseFrom(string);
        current = keyValueMap.get(prior.getKey());
        if (current.getInstanceVersion() <= prior.getInstanceVersion()) {
          throw new OptimisticLockException("Another thread/process has updated the data after it was read.");
        }
      }
    } catch (InvalidProtocolBufferException e ) {
      e.printStackTrace();
      throw new RuntimeSerializationException("Failed to read a request protocol buffer.", e);
    }
  }

  /**
   * Handles a request to get data from the database and returns an appropriate reply
   *
   * @throws RuntimeRequestException if a runtime exception occurred while fulfilling this request
   */
   Request.GetReply handleRequest(GetRequest request) {
    List<ByteString> result;
    ErrorCondition condition = ErrorCondition.getDefaultInstance();
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
   * Handles a request to get data from the database and returns an appropriate reply
   *
   * @throws RuntimeRequestException if a runtime exception occurred while fulfilling this request
   */
   Request.GetReply handleRequest(GetRangeRequest request) {
    List<ByteString> result;
    ErrorCondition condition = ErrorCondition.getDefaultInstance();
    try {
      Request.GetRangeRequestBody body = request.getBody();
      result = db().getRange(body.getStartKey().toByteArray(), body.getEndKey().toByteArray());
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

  private GetReply getGetReply(GetRequest request, List<ByteString> result, ErrorCondition condition) {
    return GetReply.newBuilder()
        .setRequestId(request.getHeader().getId())
        .setTimestamp(Instant.now().toEpochMilli())
        .addAllDocumentBytes(result)
        .setErrorCondition(condition)
        .build();
  }

  private GetReply getGetReply(GetRangeRequest request, List<ByteString> result, ErrorCondition condition) {
    return GetReply.newBuilder()
        .setRequestId(request.getHeader().getId())
        .setTimestamp(Instant.now().toEpochMilli())
        .addAllDocumentBytes(result)
        .setErrorCondition(condition)
        .build();
  }

  /**
   * Returns an error condition object that represents a 'no error' state
   */
  private ErrorCondition getNoErrorConditionForWrite(WriteRequest request) {
    ErrorCondition condition;
    condition = ErrorCondition.newBuilder()
        .setDescription("")
        .setErrorType(Request.ErrorType.NONE)
        .setRequestId(request.getHeader().getId())
        .setToken(ByteString.copyFrom(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8)))
        .setDescription("No error")
        .setOrigin(Request.ErrorOrigin.SERVER)
        .build();
    return condition;
  }

  private ErrorCondition getOptimisticLockErrorCondition(WriteRequest request, OptimisticLockException e) {
    ErrorCondition errorCondition;
    errorCondition = ErrorCondition.newBuilder()
        .setErrorType(Request.ErrorType.OPTIMISTIC_LOCK_EXCEPTION)
        .setOrigin(Request.ErrorOrigin.SERVER)
        .setRequestId(request.getHeader().getId())
        .setDescription(e.getMessage())
        .build();
    return errorCondition;
  }

  private WriteReply getWriteReply(WriteRequest request, ErrorCondition errorCondition) {
    return WriteReply.newBuilder()
        .setRequestId(request.getHeader().getId())
        .setTimestamp(Instant.now().toEpochMilli())
        .setErrorCondition(errorCondition)
        .build();
  }
}
