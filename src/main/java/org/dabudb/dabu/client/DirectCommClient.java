package org.dabudb.dabu.client;

import org.dabudb.dabu.server.DirectCommServer;
import org.dabudb.dabu.shared.protobufs.Request;

/**
 * A communication client that communicates directly with an in-process db server
 */
public class DirectCommClient implements CommClient {

  private final DirectCommServer directCommServer;

  public DirectCommClient() {
    directCommServer = new DirectCommServer();
  }

  @Override
  public Request.WriteReply sendRequest(Request.WriteRequest request) {
    return directCommServer.handleRequest(request);
  }

  @Override
  public Request.GetReply sendRequest(Request.GetRequest request) {
    return directCommServer.handleRequest(request);
  }

  @Override
  public Request.DeleteReply sendRequest(Request.DeleteRequest request) {
    return directCommServer.handleRequest(request);
  }

  @Override
  public Request.QueryReply sendRequest(Request.QueryRequest request) {
    return directCommServer.handleRequest(request);
  }
}
