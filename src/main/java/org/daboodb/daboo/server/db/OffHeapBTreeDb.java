package org.daboodb.daboo.server.db;

import com.google.protobuf.ByteString;
import org.daboodb.daboo.server.io.DatabaseExporter;
import org.daboodb.daboo.generated.protobufs.Request;
import org.daboodb.daboo.shared.exceptions.RuntimePersistenceException;
import org.mapdb.BTreeMap;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;

/**
 * An in-memory implementation of a Db
 */
public class OffHeapBTreeDb implements Db {

  //private static final long ALLOCATED_SIZE_IN_BYTES = 1024 * 1024 * 1024;  // 1 GB
  private static final long ALLOCATED_SIZE_IN_BYTES = 1024 * 1024 * 50;  // 50 MB

  private final DB db = DBMaker
      .memoryDirectDB()
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
  public void exportDocuments(File file) {
    DatabaseExporter exporter = DatabaseExporter.get(file);
    store.forEach((k, v) -> {
      try {
        exporter.log(v);
      } catch (IOException e) {
        e.printStackTrace();
        //TODO(lwhite): Log this exception
        throw new RuntimePersistenceException("An IOException occurred exporting documents from the database", e);
      }
    });
    exporter.close();
  }

  @Override
  public void put(byte[] key, byte[] value) {
    store.put(key, value);
  }

  @Override
  public int size() {
    return store.size();
  }

  @Override
  public void clear() {
    store.clear();
  }

  @Override
  public List<ByteString> getRange(byte[] from, byte[] to) {
    List<ByteString> docs = new ArrayList<>();
    ConcurrentNavigableMap<byte[], byte[]> results = store.subMap(from, to);
    for (Map.Entry<byte[], byte[]> entry : results.entrySet()) {
      docs.add(ByteString.copyFrom(entry.getValue()));
    }
    return docs;
  }
}
