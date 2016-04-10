package org.dabudb.dabu.server.db;

import com.google.common.primitives.SignedBytes;
import org.dabudb.dabu.shared.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * An in-memory implementation of a Db
 */
public class OnHeapConcurrentSkipListDb implements Db {

  private ConcurrentSkipListMap<byte[], byte[]> store
      = new ConcurrentSkipListMap<>(SignedBytes.lexicographicalComparator());

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
