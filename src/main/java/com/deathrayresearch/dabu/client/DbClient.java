package com.deathrayresearch.dabu.client;

import com.deathrayresearch.dabu.shared.Document;

/**
 *
 */
public interface DbClient {

  void write(byte[] key, byte[] value);

  void write(Document document);

  byte[] get(byte[] key);

  void delete(byte[] key);

}
