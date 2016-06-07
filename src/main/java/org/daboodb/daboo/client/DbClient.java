package org.daboodb.daboo.client;

import com.google.protobuf.ByteString;
import org.daboodb.daboo.client.exceptions.RequestTimeoutException;
import org.daboodb.daboo.shared.DocumentUtils;
import org.daboodb.daboo.shared.exceptions.DatastoreException;
import org.daboodb.daboo.client.exceptions.RuntimeSerializationException;
import org.daboodb.daboo.shared.Document;
import org.daboodb.daboo.shared.exceptions.OptimisticLockException;
import org.daboodb.daboo.shared.exceptions.RuntimeDatastoreException;
import org.daboodb.daboo.shared.exceptions.RuntimePersistenceException;
import org.daboodb.daboo.generated.protobufs.Request;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.daboodb.daboo.shared.DocumentUtils.*;
import static org.daboodb.daboo.generated.protobufs.Request.*;
import static org.daboodb.daboo.shared.RequestUtils.*;

/**
 * The database client interface
 */
public class DbClient implements KeyValueStoreApi {

  private final ClientSettings settings = ClientSettings.getInstance();

  /**
   * Returns a DbClient instance
   */
  public static DbClient get() {
    return new DbClient();
  }

  private DbClient() {
  }

  /**
   * Writes the given document to the database as an "upsert"
   *
   * @throws OptimisticLockException      if the attempt to update a document would overwrite an unseen change to that
   *                                      document
   * @throws RequestTimeoutException      if this request failed to return within the allotted time
   * <p/>
   * @throws NullPointerException         if the document parameter is null
   * @throws RuntimeDatastoreException    if a non-recoverable error has occurred
   * @throws ConstraintViolationException if a JSR 349 validation constraint is violated by the attributes of any
   *                                      document
   */
  @Override
  public void write(@Nonnull Document document) throws DatastoreException {

    Request.Document doc = getDocument(document);
    Header header = getWriteHeader();
    WriteRequestBody body = getWriteRequestBody(doc);
    WriteRequest request = getWriteRequest(header, body);
    Request.WriteReply reply = settings.getCommClient().sendRequest(request);
    checkErrorCondition(reply.getErrorCondition());
  }

  /**
   * Writes all the documents in documentCollection to the database
   * <p>
   * All writes are "upserts"
   *
   * @throws OptimisticLockException      if the attempt to update a document would overwrite an unseen change to that
   *                                      document
   * @throws RequestTimeoutException      if this request failed to return within the allotted time
   *                                      <p/>
   * @throws NullPointerException         if the document parameter is null
   * @throws RuntimeDatastoreException    if a non-recoverable error has occurred
   * @throws ConstraintViolationException if a JSR 349 validation constraint is violated by the attributes of any
   *                                      document
   */
  @Override
  public void write(@Nonnull List<Document> documentCollection)
      throws DatastoreException {

    List<Request.Document> documentList = new ArrayList<>();
    for (Document document : documentCollection) {
      Request.Document doc = getDocument(document);
      documentList.add(doc);
    }

    Header header = getWriteHeader();
    WriteRequestBody body = getWriteRequestBody(documentList);
    WriteRequest request = getWriteRequest(header, body);
    Request.WriteReply reply = settings.getCommClient().sendRequest(request);
    checkErrorCondition(reply.getErrorCondition());
  }

  /**
   * Returns the document with the given key, or null if it doesn't exist
   *
   * @throws RequestTimeoutException   if this request failed to return within the allotted time
   *                                   <p/>
   * @throws NullPointerException      if the value of the key parameter is null
   * @throws RuntimeDatastoreException if a non-recoverable error has occurred
   */
  @Nullable
  @Override
  public Document get(@Nonnull byte[] key) throws DatastoreException {
    ByteString keyBytes = ByteString.copyFrom(key);
    Header header = getGetHeader();
    GetRequestBody body = getGetRequestBody(keyBytes);
    GetRequest request = getGetRequest(header, body);

    Request.GetReply reply = settings.getCommClient().sendRequest(request);
    checkErrorCondition(reply.getErrorCondition());

    List<ByteString> resultBytesList = reply.getDocumentBytesList();
    ByteString resultBytes;
    if (resultBytesList.isEmpty()) {
      return null;
    } else {
      resultBytes = resultBytesList.get(0);
    }
    return getDocumentFromRequestDoc(resultBytes);
  }

  /**
   * Returns a collection (possibly empty) of documents associated with the given list of keys
   *
   * @throws RequestTimeoutException   if this request failed to return within the allotted time
   *                                   <p/>
   * @throws NullPointerException      if the value of the keys parameter is null, or keys contains any null entries
   * @throws RuntimeDatastoreException if a non-recoverable error has occurred
   */
  @Override
  public List<Document> get(@Nonnull List<byte[]> keys) throws DatastoreException {
    Header header = getGetHeader();
    List<ByteString> byteStrings = keys.stream().map(ByteString::copyFrom).collect(Collectors.toList());
    GetRequestBody body = getGetRequestBody(byteStrings);
    GetRequest request = getGetRequest(header, body);
    Request.GetReply reply = settings.getCommClient().sendRequest(request);
    checkErrorCondition(reply.getErrorCondition());
    List<ByteString> byteStringList = reply.getDocumentBytesList();
    return byteStringList.stream().map(DocumentUtils::getDocumentFromRequestDoc).collect(Collectors.toList());
  }

  /**
   * Returns a collection (possibly empty) of documents with keys between the given range
   *
   * @throws RequestTimeoutException   if this request failed to return within the allotted time
   *                                   <p/>
   * @throws NullPointerException      if the value of the keys parameter is null, or keys contains any null entries
   * @throws RuntimeDatastoreException if a non-recoverable error has occurred
   */
  public List<Document> getRangeRequest(@Nonnull byte[] startKey, byte[] endKey) throws DatastoreException {
    Header header = getGetHeader();
    GetRangeRequestBody body = getGetRangeRequestBody(ByteString.copyFrom(startKey), ByteString.copyFrom(endKey));
    GetRangeRequest request = getGetRangeRequest(header, body);
    Request.GetReply reply = settings.getCommClient().sendRequest(request);
    checkErrorCondition(reply.getErrorCondition());
    List<ByteString> byteStringList = reply.getDocumentBytesList();
    return byteStringList.stream().map(DocumentUtils::getDocumentFromRequestDoc)
        .collect(Collectors.toList());
  }

  /**
   * Deletes the given document if it exists in the database
   * <p>
   * Does nothing if the document does not exist
   * <p/>
   *
   * @throws OptimisticLockException   if the attempt to delete a document would overwrite an unseen change to that
   *                                   document
   * @throws RequestTimeoutException   if this request failed to return within the allotted time
   *                                   <p/>
   * @throws NullPointerException      if the documents parameter is null
   * @throws RuntimeDatastoreException if a non-recoverable error has occurred
   */
  @Override
  public void delete(@Nonnull Document document) throws DatastoreException {
    Request.Document doc = getDocument(document);
    Header header = getDeleteHeader();
    DeleteRequestBody body = getDeleteRequestBody(doc);
    WriteRequest request = getDeleteRequest(header, body);
    Request.WriteReply reply = settings.getCommClient().sendRequest(request);
    checkErrorCondition(reply.getErrorCondition());
  }

  /**
   * Deletes all the given documents that exist in the database
   * <p>
   * Any documents not in the database are ignored
   *
   * @throws OptimisticLockException   if the attempt to delete a document would overwrite an unseen change to that
   *                                   document
   * @throws RequestTimeoutException   if this request failed to return within the allotted time
   *                                   <p/>
   * @throws NullPointerException      if the documents parameter is null
   * @throws RuntimeDatastoreException if a non-recoverable error has occurred
   */
  @Override
  public void delete(@Nonnull List<Document> documents) throws DatastoreException {
    List<Request.Document> docs = new ArrayList<>();

    for (Document document : documents) {
      Request.Document doc = getDocument(document);
      docs.add(doc);
    }

    Header header = getDeleteHeader();
    DeleteRequestBody body = getDeleteRequestBody(docs);
    WriteRequest request = getDeleteRequest(header, body);
    Request.WriteReply reply = settings.getCommClient().sendRequest(request);
    checkErrorCondition(reply.getErrorCondition());
  }

  private void checkErrorCondition(ErrorCondition condition) throws DatastoreException {
    if (condition != null && condition.getErrorType() != ErrorType.NONE) {
      System.out.println(condition);
      ErrorType type = condition.getErrorType();

      switch (type) {
        case SERIALIZATION_EXCEPTION:
          //TODO(lwhite): decide how to handle the various serialization issues. Which, if any, are recoverable?
          throw new RuntimeSerializationException(null, null);
        case OPTIMISTIC_LOCK_EXCEPTION:
          throw new OptimisticLockException(condition.getDescription());
        case PERSISTENCE_EXCEPTION:
          //TODO(lwhite): throw this exception without the null param
          throw new RuntimePersistenceException(condition.getDescription(), null);
        default:
          //TODO(lwhite): throw this exception without the null param
          throw new RuntimeDatastoreException("An unhandled error-type condition occurred", null);
      }
    }
  }
}
