package org.dabudb.dabu.server.db;

import com.google.protobuf.ByteString;
import org.dabudb.dabu.shared.protobufs.Request;

import java.util.List;
import java.util.Map;

/**
 * The main interface for the database (server-side)
 */
public interface Db extends Iterable<Map.Entry<byte[], byte[]>> {

  void write(List<Request.Document> documentList);

  void delete(List<Request.Document> key);

  List<ByteString> get(List<ByteString> keys);

}
