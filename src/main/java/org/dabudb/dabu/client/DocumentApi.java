package org.dabudb.dabu.client;

import org.dabudb.dabu.shared.Document;

import java.util.List;

/**
 *
 */
public interface DocumentApi {

  void write(List<Document> documentCollection);

  void write(Document document);

  Document get(byte[] key);

  List<Document> get(List<byte[]> keys);

  void delete(Document document);

  void delete(List<Document> document);


}
