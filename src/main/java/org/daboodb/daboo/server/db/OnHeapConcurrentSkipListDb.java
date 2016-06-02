package org.daboodb.daboo.server.db;

import com.google.common.primitives.SignedBytes;
import com.google.protobuf.ByteString;
import org.daboodb.daboo.server.io.DatabaseExporter;
import org.daboodb.daboo.generated.protobufs.Request;
import org.daboodb.daboo.shared.exceptions.RuntimePersistenceException;
import org.daboodb.daboo.shared.util.logging.LoggerWriter;
import org.daboodb.daboo.shared.util.logging.LoggingFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

/**
 * An in-memory implementation of a Db
 */
public class OnHeapConcurrentSkipListDb implements Db {

  private LoggerWriter loggerWriter = LoggingFactory.getLogger(this.getClass());

  private final ConcurrentSkipListMap<byte[], byte[]> store
      = new ConcurrentSkipListMap<>(SignedBytes.lexicographicalComparator());

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

  public void exportDocuments(File file) {
    DatabaseExporter exporter = DatabaseExporter.get(file);
    store.forEach((k, v) -> {
      try {
        System.out.println(Arrays.toString(k));
        exporter.log(v);
      } catch (IOException e) {
        e.printStackTrace();
        loggerWriter.logError("Failed to export documents to the database file " + file.toString(), e);
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
    docs.addAll(results.entrySet().stream()
        .map(entry -> ByteString.copyFrom(entry.getValue())).collect(Collectors.toList()));
    return docs;
  }
}
