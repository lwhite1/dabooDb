package org.dabudb.dabu.server.db;

import com.google.protobuf.ByteString;
import org.dabudb.dabu.shared.protobufs.Request;

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

  void export(File file);

}
