package org.dabudb.dabu.client;

import org.dabudb.dabu.shared.Document;

import java.util.List;

/**
 *
 */
public interface DocumentApi {

  void writeDoc(Document document);

  void writeDocs(List<Document> documentCollection);

  Document getDoc(byte[] key);

  List<Document> getDocs(List<byte[]> keys);

  void deleteDoc(byte[] key);

  void deleteDocs(List<byte[]> keys);

}
