package com.deathrayresearch.dabu.client;

import com.deathrayresearch.dabu.server.DbServer;
import com.deathrayresearch.dabu.shared.Reply;
import com.deathrayresearch.dabu.shared.Request;

/**
 * A communication client that communicates directly with an in-process db server
 */
public class DirectCommunicationClient implements CommunicationClient {

  private final DbServer dbServer = new DbServer();

  @Override
  public Reply sendRequest(Request request) {
    return dbServer.handleRequest(request);
  }
}
