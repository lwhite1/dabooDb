package org.dabudb.dabu.shared;

/**
 *
 */
public interface DocumentSerializer {

  byte[] documentToBytes(Document document);

  Document bytesToDocument(Class documentClass, byte[] documentBytes);
}
