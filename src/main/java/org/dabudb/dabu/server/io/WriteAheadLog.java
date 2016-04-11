package org.dabudb.dabu.server.io;


import org.dabudb.dabu.shared.protobufs.Request;

import java.io.IOException;

/**
 *
 */
public interface WriteAheadLog {

  void logRequest(Request.WriteRequest request) throws IOException;

  void logRequest(Request.DeleteRequest request) throws IOException;

  void replay();
}
