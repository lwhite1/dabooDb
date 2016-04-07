package com.deathrayresearch.dabu.server;

import com.google.common.primitives.SignedBytes;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * An in-memory implementation of a Db
 */
public class MemoryDb implements Db {

  private TreeMap<byte[], byte[]> store = new TreeMap<>(SignedBytes.lexicographicalComparator());

  @Override
  public void write(byte[] key, byte[] value) {
    store.put(key, value);
  }

  @Override
  public void remove(byte[] key) {
    store.remove(key);
  }

  @Override
  public byte[] get(byte[] key) {
    return store.get(key);
  }

  @Override
  public Iterator<Map.Entry<byte[], byte[]>> iterator() {
    return store.entrySet().iterator();
  }
}
