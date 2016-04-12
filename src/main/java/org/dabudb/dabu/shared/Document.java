package org.dabudb.dabu.shared;

/**
 *  A document is the basic unit of persistence
 */
public interface Document {

  /**
   * Returns the primary key by which this document can be found in the db
   */
  byte[] key();

  /**
   * Returns an integer representing the number of times the document has been saved.
   * This value is used for optimistic lock checking
   */
  int documentVersion();

  /**
   * Returns the document's contents, a domain model object as a byte array
   */
  byte[] getContents();

  /**
   * Returns a String naming the kind of document represented by the content
   */
  String getContentType();

  /**
   * Returns a String naming the kind of document represented by the content
   */
  String getContentClass();

  int schemaVersion();

  void setContents(byte[] contents);

  void setInstanceVersion(int documentVersion);

  void setKey(byte[] key);

  void setContentType(String contentType);

  void setContentClass(String contentClass);

  int getInstanceVersion();

  short getSchemaVersion();

  void setSchemaVersion(short schemaVersion);

  DocumentContents documentContents();

  byte[] marshall();
}
