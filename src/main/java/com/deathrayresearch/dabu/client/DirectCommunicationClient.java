package com.deathrayresearch.dabu.client;

import com.deathrayresearch.dabu.server.DbServer;
import com.deathrayresearch.dabu.shared.msg.DocumentGetReply;
import com.deathrayresearch.dabu.shared.msg.DocumentGetRequest;
import com.deathrayresearch.dabu.shared.msg.Reply;
import com.deathrayresearch.dabu.shared.msg.Request;

/**
 * A communication client that communicates directly with an in-process db server
 */
public class DirectCommunicationClient implements CommunicationClient {

  private final DbServer dbServer;

  public DirectCommunicationClient() {
    dbServer = new DbServer();
  }

  @Override
  public Reply sendRequest(Request request) {
    return dbServer.handleRequest(request);
  }

  @Override
  public DocumentGetReply sendRequest(DocumentGetRequest request) {
    return (DocumentGetReply) dbServer.handleRequest(request);
  }
}
