package org.dabudb.dabu.server.io;

import org.dabudb.dabu.shared.msg.request.Request;

import java.io.IOException;

/**
 *
 */
public interface WriteAheadLog {

  void logRequest(Request request) throws IOException;

  void replay();
}
