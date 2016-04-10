package org.dabudb.dabu.server;

import org.dabudb.dabu.shared.Document;

import java.util.List;
import java.util.Map;

/**
 * The main interface for the database (server-side)
 */
public interface Db extends Iterable<Map.Entry<byte[], byte[]>> {

  void write(byte[] key, byte[] value);

  void write(List<Document> documentList);

  void remove(byte[] key);

  byte[] get(byte[] key);

  List<byte[]> get(List<byte[]> keys);

}
