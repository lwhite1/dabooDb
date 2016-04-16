package org.dabudb.dabu.server.db;

import com.google.protobuf.ByteString;
import org.dabudb.dabu.generated.protobufs.Request;
import org.dabudb.dabu.server.io.DatabaseExporter;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * The main interface for the database (server-side)
 */
public interface Db extends Iterable<Map.Entry<byte[], byte[]>> {

  void write(Map<byte[], byte[]> documentMap);

  void delete(List<Request.Document> documentList);

  List<ByteString> get(List<ByteString> keyList);

  /**
   * Exports all documents to the given file
   */
  void exportDocuments(File file);

  /**
   * Imports all documents in the given file
   */
  default void importDocuments(File file) {
    DatabaseExporter exporter = DatabaseExporter.getInstance(file);
    while (exporter.hasNext()) {
      Request.Document document = exporter.next();
      put(document.getKey().toByteArray(), document.toByteArray());
    }
  }

  void put(byte[] key, byte[] value);

  int size();

  default boolean isEmpty() {
    return size() == 0;
  }
}
