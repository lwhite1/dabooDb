package org.dabudb.dabu.client;

import com.google.protobuf.ByteString;
import org.dabudb.dabu.client.exceptions.RequestTimeoutException;
import org.dabudb.dabu.shared.exceptions.DatastoreException;
import org.dabudb.dabu.client.exceptions.RuntimeSerializationException;
import org.dabudb.dabu.shared.Document;
import org.dabudb.dabu.shared.exceptions.OptimisticLockException;
import org.dabudb.dabu.shared.exceptions.RuntimeDatastoreException;
import org.dabudb.dabu.shared.exceptions.RuntimePersistenceException;
import org.dabudb.dabu.generated.protobufs.Request;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.dabudb.dabu.shared.DocumentUtils.*;
import static org.dabudb.dabu.generated.protobufs.Request.*;
import static org.dabudb.dabu.shared.RequestUtils.*;

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
   * @throws OptimisticLockException   if the attempt to update a document would overwrite an unseen change to that
   * document
   * @throws RequestTimeoutException   if this request failed to return within the allotted time
   * <p/>
   * @throws NullPointerException      if the document parameter is null
   * @throws RuntimeDatastoreException if a non-recoverable error has occurred
   */
  @Override
  public void write(@Nonnull Document document) throws DatastoreException {

    Request.Document doc = getDocument(document);
    Header header = getHeader();
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
   * @throws OptimisticLockException   if the attempt to update a document would overwrite an unseen change to that
   * document
   * @throws RequestTimeoutException   if this request failed to return within the allotted time
   * <p/>
   * @throws NullPointerException      if the document parameter is null
   * @throws RuntimeDatastoreException if a non-recoverable error has occurred

   */
  @Override
  public void write(@Nonnull List<Document> documentCollection)
      throws DatastoreException {

    List<Request.Document> documentList = new ArrayList<>();
    for (Document document : documentCollection) {
      Request.Document doc = getDocument(document);
      documentList.add(doc);
    }

    Header header = getHeader();
    WriteRequestBody body = getWriteRequestBody(documentList);
    WriteRequest request = getWriteRequest(header, body);
    Request.WriteReply reply = settings.getCommClient().sendRequest(request);
    checkErrorCondition(reply.getErrorCondition());
  }

  /**
   * Returns the document with the given key, or null if it doesn't exist
   *
   * @throws RequestTimeoutException   if this request failed to return within the allotted time
   * <p/>
   * @throws NullPointerException      if the value of the key parameter is null
   * @throws RuntimeDatastoreException if a non-recoverable error has occurred
   */
  @Nullable
  @Override
  public Document get(@Nonnull byte[] key) throws DatastoreException {
    ByteString keyBytes = ByteString.copyFrom(key);
    Header header = getHeader();
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
    return getDocumentFromRequestDoc(settings.getDocumentClass(), resultBytes);
  }

  /**
   * Returns a collection (possibly empty) of documents associated with the given list of keys
   *
   * @throws RequestTimeoutException   if this request failed to return within the allotted time
   * <p/>
   * @throws NullPointerException      if the value of the keys parameter is null, or keys contains any null entries
   * @throws RuntimeDatastoreException if a non-recoverable error has occurred
   */
  @Override
  public List<Document> get(@Nonnull List<byte[]> keys) throws DatastoreException {
    Header header = getHeader();
    List<ByteString> byteStrings = keys.stream().map(ByteString::copyFrom).collect(Collectors.toList());
    GetRequestBody body = getGetRequestBody(byteStrings);
    GetRequest request = getGetRequest(header, body);
    Request.GetReply reply = settings.getCommClient().sendRequest(request);
    checkErrorCondition(reply.getErrorCondition());
    List<ByteString> byteStringList = reply.getDocumentBytesList();
    return byteStringList.stream().map(bytes -> getDocumentFromRequestDoc(settings.getDocumentClass(), bytes))
        .collect(Collectors.toList());
  }

  /**
   * Deletes the given document if it exists in the database
   * <p>
   * Does nothing if the document does not exist
   * <p/>
   * @throws OptimisticLockException   if the attempt to delete a document would overwrite an unseen change to that
   * document
   * @throws RequestTimeoutException   if this request failed to return within the allotted time
   * <p/>
   * @throws NullPointerException      if the documents parameter is null
   * @throws RuntimeDatastoreException if a non-recoverable error has occurred
   */
  @Override
  public void delete(@Nonnull Document document) throws DatastoreException {
    Request.Document doc = getDocument(document);
    Header header = getHeader();
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
   * document
   * @throws RequestTimeoutException   if this request failed to return within the allotted time
   * <p/>
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

    Header header = getHeader();
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
