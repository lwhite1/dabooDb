package com.deathrayresearch.dabu.client;

import com.deathrayresearch.dabu.server.DbServer;
import com.deathrayresearch.dabu.shared.msg.GetReply;
import com.deathrayresearch.dabu.shared.msg.GetRequest;
import com.deathrayresearch.dabu.shared.msg.Reply;
import com.deathrayresearch.dabu.shared.msg.Request;

/**
 * A communication client that communicates directly with an in-process db server
 */
public class DirectCommunicationClient implements CommunicationClient {

  private final DbServer dbServer;

  public DirectCommunicationClient() {
    dbServer = DbServer.INSTANCE;
  }

  @Override
  public Reply sendRequest(Request request) {
    return dbServer.handleRequest(request);
  }

  @Override
  public GetReply sendRequest(GetRequest request) {
    return (GetReply) dbServer.handleRequest(request);
  }
}
