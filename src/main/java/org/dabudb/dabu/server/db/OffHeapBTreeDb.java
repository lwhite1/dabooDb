package org.dabudb.dabu.server.db;

import com.google.protobuf.ByteString;
import org.dabudb.dabu.server.io.DatabaseExporter;
import org.dabudb.dabu.generated.protobufs.Request;
import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * An in-memory implementation of a Db
 */
public class OffHeapBTreeDb implements Db {

  //private static final long ALLOCATED_SIZE_IN_BYTES = 1024 * 1024 * 1024;  // 1 GB
  private static final long ALLOCATED_SIZE_IN_BYTES = 1024 * 1024 * 50;  // 50 MB

  private final DB db = DBMaker
      .memoryDB()
      .allocateStartSize(ALLOCATED_SIZE_IN_BYTES)
      .make();

  private final BTreeMap<byte[], byte[]> store = db
      .treeMap("treemap", Serializer.BYTE_ARRAY, Serializer.BYTE_ARRAY)
      .make();

  @Override
  public void write(Map<byte[], byte[]> documentMap) {
    store.putAll(documentMap);
  }

  @Override
  public void delete(List<Request.Document> documentList) {
    for (Request.Document doc : documentList) {
      store.remove(doc.getKey().toByteArray());
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
