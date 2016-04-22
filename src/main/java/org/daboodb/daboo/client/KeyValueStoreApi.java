package org.daboodb.daboo.client;

import org.daboodb.daboo.shared.exceptions.DatastoreException;
import org.daboodb.daboo.shared.Document;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Interface for basic key value functionality implemented in DbClient
 */
interface KeyValueStoreApi {

  /**
   * Writes all the documents in documentCollection to the database
   *
   * All writes are "upserts"
   */
  void write(@Nonnull List<Document> documentCollection) throws DatastoreException;

  /**
   * Writes the given document to the database as an "upsert"
   */
  void write(@Nonnull Document document) throws DatastoreException;

  /**
   * Returns the document with the given key, or null if it doesn't exist
   */
  @Nullable
  Document get(@Nonnull byte[] key) throws DatastoreException;

  /**
   * Returns a collection (possibly empty) of documents associated with the given list of keys
   */
  List<Document> get(@Nonnull List<byte[]> keys) throws DatastoreException;

  /**
   * Deletes the given document if it exists in the database
   *
   * Does nothing if the document does not exist
   */
  void delete(@Nonnull Document document) throws DatastoreException;

  /**
   * Deletes all the given documents that exist in the database
   *
   * Any documents not in the database are ignored
   */
  void delete(@Nonnull List<Document> document) throws DatastoreException;
}
