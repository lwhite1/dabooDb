package org.dabudb.dabu.server.db;

import org.dabudb.dabu.shared.Document;
import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * An in-memory implementation of a Db
 */
public class OffHeapBTreeDb implements Db {

  long ALLOCATED_SIZE_IN_BYTES = 1024 * 1024 * 1024;  // 1 GB

  private DB db = DBMaker
      .memoryDB()
      .allocateStartSize(ALLOCATED_SIZE_IN_BYTES)
      .make();

  BTreeMap<byte[],byte[]> store = db
      .treeMap("treemap", Serializer.BYTE_ARRAY, Serializer.BYTE_ARRAY)
      .make();

  @Override
  public void write(byte[] key, byte[] value) {
    store.put(key, value);
  }

  @Override
  public void write(List<Document> documentList) {
    //TODO(lwhite): review performance here:
    Map<byte[], byte[]> documentMap = new HashMap<>();
    for (Document doc : documentList) {
      documentMap.put(doc.key(), doc.marshall());
    }
    store.putAll(documentMap);
  }

  @Override
  public void delete(byte[] key) {
    store.remove(key);
  }

  @Override
  public void delete(List<byte[]> keys) {
    for (byte[] key : keys) {
      store.remove(key);
    }
  }

  @Override
  public byte[] get(byte[] key) {
    return store.get(key);
  }

  @Override
  public List<byte[]> get(List<byte[]> keys) {
    List<byte[]> docs = new ArrayList<>();
    for (byte[] key : keys) {
      docs.add(store.get(key));
    }
    return docs;
  }

  @Override
  public Iterator<Map.Entry<byte[], byte[]>> iterator() {
    return store.entrySet().iterator();
  }
}
