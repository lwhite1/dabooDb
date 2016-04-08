package com.deathrayresearch.dabu.client;

import com.deathrayresearch.dabu.shared.Document;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public interface DocumentApi {

  void writeDoc(Document document);

  void writeDocs(Collection<Document> documentCollection);

  Document getDoc(byte[] key);

  List<Document> getDocs(Collection<byte[]> keys);

  void deleteDoc(byte[] key);

  void deleteDocs(Collection<byte[]> keys);

}
