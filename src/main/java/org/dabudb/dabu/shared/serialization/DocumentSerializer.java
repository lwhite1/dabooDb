package org.dabudb.dabu.shared.serialization;

import org.dabudb.dabu.shared.Document;

/**
 * A serializer that operates on Documents, as opposed to on their contents.
 * We use this because we write documents in the database as byte arrays
 */
public interface DocumentSerializer {

  /**
   * Returns a byte array representation of the given document
   */
  byte[] documentToBytes(Document document);

  /**
   * Returns a Document reference object from the given byte array,
   * assuming of course that's actually what's in the byte array, and that it was serialized to bytes
   * using the same approach.
   *
   * @param documentClass  A class implementing Document
   * @param documentBytes  A byte array containing a serialized document of the given class
   */
  Document bytesToDocument(Class documentClass, byte[] documentBytes);
}
