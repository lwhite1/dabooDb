package com.deathrayresearch.dabu.client;

/**
 *
 */
public interface DbClient {

  void write(byte[] key, byte[] value);

  byte[] get(byte[] key);

  void delete(byte[] key);


}
