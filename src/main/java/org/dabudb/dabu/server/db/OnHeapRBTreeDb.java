package org.dabudb.dabu.server.db;

import com.google.common.primitives.SignedBytes;
import com.google.protobuf.ByteString;
import org.dabudb.dabu.server.io.DatabaseExporter;
import org.dabudb.dabu.generated.protobufs.Request;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * An in-memory implementation of a Db
 */
public class OnHeapRBTreeDb implements Db {

  private final TreeMap<byte[], byte[]> store = new TreeMap<>(SignedBytes.lexicographicalComparator());

  @Override
  public void write(Map<byte[], byte[]> documentMap) {
    store.putAll(documentMap);
  }

  @Override
  public void delete(List<Request.Document> documentList) {
    for (Request.Document document : documentList) {
      store.remove(document.getKey().toByteArray());
    }
  }

  @Override
  public List<ByteString> get(List<ByteString> keyList) {
    List<ByteString> docs = new ArrayList<>();
    for (ByteString key : keyList) {
      byte[] result = store.get(key.toByteArray());
      if (result != null) {
        docs.add(ByteString.copyFrom(result));
      }
    }
    return docs;
  }

  @Override
  public void export(File file) {
    DatabaseExporter exporter = DatabaseExporter.getInstance(file);
    store.forEach((k, v) -> {
      try {
        exporter.log(v);
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  @Override
  public Iterator<Map.Entry<byte[], byte[]>> iterator() {
    return store.entrySet().iterator();
  }
}
