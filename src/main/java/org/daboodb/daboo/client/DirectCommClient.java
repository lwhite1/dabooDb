package org.daboodb.daboo.client;

import org.daboodb.daboo.server.DirectCommServer;
import org.daboodb.daboo.generated.protobufs.Request;

/**
 * A communication client that communicates directly with an in-process db server
 */
public class DirectCommClient implements CommClient {

  private final DirectCommServer directCommServer;

  DirectCommClient() {
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
  public Request.GetReply sendRequest(Request.GetRangeRequest request) {
    return directCommServer.handleRequest(request);
  }
}
