package com.deathrayresearch.dabu.server;

import com.deathrayresearch.dabu.shared.Document;
import com.google.common.primitives.SignedBytes;
import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * An in-memory implementation of a Db
 */
public class MemoryDb implements Db {

  //private ConcurrentSkipListMap<byte[], byte[]> store
  //    = new ConcurrentSkipListMap<>(SignedBytes.lexicographicalComparator());
  private TreeMap<byte[], byte[]> store = new TreeMap<>(SignedBytes.lexicographicalComparator());
/*
  private DB db = DBMaker
      .memoryDB()
      .make();

  BTreeMap<byte[],byte[]> store = db
      .treeMap("treemap", Serializer.BYTE_ARRAY, Serializer.BYTE_ARRAY)
      .make();
*/

  @Override
  public void write(byte[] key, byte[] value) {
    store.put(key, value);
  }

  @Override
  public void write(List<Document> documentList) {

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
  public List<byte[]> get(List<byte[]> keys) {
    return null;
  }

  @Override
  public Iterator<Map.Entry<byte[], byte[]>> iterator() {
    return store.entrySet().iterator();
  }
}
