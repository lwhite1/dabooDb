package com.deathrayresearch.dabu.server.io;

import com.deathrayresearch.dabu.shared.msg.Request;

/**
 *
 */
public interface WriteAheadLog {

  void logRequest(Request request);

  void replay();
}
