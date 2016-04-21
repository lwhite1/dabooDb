package org.daboodb.daboo.shared;

/**
 * The interface to be implemented by any class to be persisted
 */
public interface DocumentContents {

  /**
   * Returns the key to be used as the primary database key for this object
   */
  byte[] getKey();

  /**
   * Returns a string, analogous to a database table name, that describes what kind of object this is
   */
  String getContentType();
}
