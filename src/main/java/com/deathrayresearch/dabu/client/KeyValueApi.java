package com.deathrayresearch.dabu.client;

import com.deathrayresearch.dabu.shared.Document;

/**
 *
 */
public interface KeyValueApi {

  void write(byte[] key, byte[] value);

  byte[] get(byte[] ... key);

  void delete(byte[] ... key);

}
