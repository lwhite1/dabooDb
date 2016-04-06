package com.deathrayresearch.dabu.server;

import java.util.Map;

/**
 * The main interface for the database (server-side)
 */
public interface Db extends Iterable<Map.Entry<byte[], byte[]>> {

  void write(byte[] key, byte[] value);

  void remove(byte[] key);

  byte[] get(byte[] key);

}
