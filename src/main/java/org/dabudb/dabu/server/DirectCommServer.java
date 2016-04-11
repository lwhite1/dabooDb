package org.dabudb.dabu.server;

import org.dabudb.dabu.shared.protobufs.Request;

/**
 *
 */
public class DirectCommServer implements CommServer {

  private final DbServer dbServer = DbServer.get();

  public Request.WriteReply handleRequest(Request.WriteRequest request) {
    return dbServer.handleRequest(request);
  }

  /**
   */
  public Request.DeleteReply handleRequest(Request.DeleteRequest request) {
    return dbServer.handleRequest(request);
  }

  /**
   */
  public Request.QueryReply handleRequest(Request.QueryRequest request) {
    return dbServer.handleRequest(request);
  }

  /**
   */
  public Request.GetReply handleRequest(Request.GetRequest request) {
    return dbServer.handleRequest(request);
  }
}
