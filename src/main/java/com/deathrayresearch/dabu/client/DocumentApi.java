package com.deathrayresearch.dabu.client;

import com.deathrayresearch.dabu.shared.Document;

/**
 *
 */
public interface DocumentApi {

  void write(Document ... document);

  Document getDoc(byte[] ... key);

  void deleteDoc(byte[] ... key);
}
