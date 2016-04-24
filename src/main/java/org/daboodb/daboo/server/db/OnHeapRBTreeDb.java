package org.daboodb.daboo.server.db;

import com.google.common.primitives.SignedBytes;
import com.google.protobuf.ByteString;
import org.daboodb.daboo.server.io.DatabaseExporter;
import org.daboodb.daboo.generated.protobufs.Request;
import org.daboodb.daboo.shared.exceptions.RuntimePersistenceException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
    SortedMap<byte[], byte[]> results = store.subMap(from, to);
    docs.addAll(results.entrySet().stream()
        .map(entry -> ByteString.copyFrom(entry.getValue())).collect(Collectors.toList()));
    return docs;
  }
}
