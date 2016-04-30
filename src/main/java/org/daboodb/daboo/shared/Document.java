package org.daboodb.daboo.shared;

/**
 * A document is the basic unit of persistence
 */
public interface Document {

  /**
   * Returns an integer representing the number of times the document has been saved.
   * This value is used for optimistic lock checking
   */
  int instanceVersion();

  /**
   * Returns the document's serialized as a byte array
   */
  byte[] serialized();

  /**
   * Returns a String containing the canonical name of the class
   * This method is used in deserialization
   */
  String getContentClass();

  /**
   * Returns the version number of the Json schema used when the object was last serialized
   */
  int schemaVersion();

  /**
   * Sets the instance version, which should be updated whenever a new version of the document is saved
   *
   * @param instanceVersion The new instance version. It should always be > the current version
   */
  void setInstanceVersion(int instanceVersion);

  /**
   * Sets the document's key, which is essentially the primary key for the object, and is used to retrieve it from
   * the database
   *
   * @param key A globally unique byte[]
   */
  void setKey(byte[] key);

  /**
   * Sets a name, analogous to a database table name for this kind of document
   *
   * @param contentType This should generally not be changed after the document is persisted
   */
  void setContentType(String contentType);

  /**
   * Returns the canonical class name for the document serialized.
   * <p/>
   * NOTE: This class name can never change without manually migrating the database
   *
   * @param contentClass A canonical class name
   */
  void setContentClass(String contentClass);

  /**
   * Sets the global json schema version that was in effect (and thus used to serialize the object) when this
   * document was saved
   *
   * @param schemaVersion A number indicating what schema version was used to serialize the document
   */
  void setSchemaVersion(short schemaVersion);


  /**
   * Returns the key to be used as the primary database key for this object
   */
  byte[] getKey();

  /**
   * Returns a String naming the kind of document represented by the content
   * <p/>
   * This string will be used to query data, and is somewhat analogous to a "table name".
   * For example, if you have two kinds of invoices, implemented by different classes and want to query them together,
   * you could give them both the same contentType.
   */
  String getContentType();
}
