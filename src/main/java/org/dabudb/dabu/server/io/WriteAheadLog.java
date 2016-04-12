package org.dabudb.dabu.server.io;


import org.dabudb.dabu.shared.protobufs.Request;

import java.io.IOException;
import java.util.Iterator;

/**
 *
 */
public interface WriteAheadLog extends Iterator<byte[]> {

  void logRequest(Request.WriteRequest request) throws IOException;

  void logRequest(Request.DeleteRequest request) throws IOException;

  void log(byte[] request) throws IOException;

}
