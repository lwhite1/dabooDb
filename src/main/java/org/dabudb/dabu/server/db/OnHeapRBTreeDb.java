package org.dabudb.dabu.server.db;

import com.google.common.primitives.SignedBytes;
import com.google.protobuf.ByteString;
import org.dabudb.dabu.shared.protobufs.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * An in-memory implementation of a Db
 */
public class OnHeapRBTreeDb implements Db {

  private TreeMap<byte[], byte[]> store = new TreeMap<>(SignedBytes.lexicographicalComparator());

  @Override
  public void write(List<Request.Document> documentList) {
    Map<byte[], byte[]> documentMap = new HashMap<>();
    for (Request.Document doc : documentList) {
      documentMap.put(doc.getKey().toByteArray(), doc.toByteArray());
    }
    store.putAll(documentMap);
  }

  @Override
  public void delete(List<Request.Document> docs) {
    for (Request.Document document : docs) {
      store.remove(document.getKey().toByteArray());
    }
  }

  @Override
  public List<ByteString> get(List<ByteString> keys) {
    List<ByteString> docs = new ArrayList<>();
    for (ByteString key : keys) {
      byte[] result = store.get(key.toByteArray());
      if (result != null) {
        docs.add(ByteString.copyFrom(result));
      }
    }
    return docs;
  }

  @Override
  public Iterator<Map.Entry<byte[], byte[]>> iterator() {
    return store.entrySet().iterator();
  }
}
