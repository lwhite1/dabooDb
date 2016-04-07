package com.deathrayresearch.dabu.server.io;

import com.deathrayresearch.dabu.shared.msg.Request;

import java.io.IOException;

/**
 *
 */
public interface WriteAheadLog {

  void logRequest(Request request) throws IOException;

  void replay();
}
