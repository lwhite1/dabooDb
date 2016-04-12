package org.dabudb.dabu.server;

import org.dabudb.dabu.shared.protobufs.Request;

/**
 * An implementation of CommServer that communicates in-process via direct message sends
 */
public class DirectCommServer implements CommServer {

  /** The database server I work for */
  private final DbServer dbServer = DbServer.get();

  /**
   * Passes a write request (insert, update, or delete) to the database server for processing
   * @param request The write request
   * @return  A WriteReply, which may contain an ErrorCondition object signifying a problem with the write
   */
  public Request.WriteReply handleRequest(Request.WriteRequest request) {
    return dbServer.handleRequest(request, request.toByteArray());
  }

  /**
   * Passes a get request to the database server for processing
   * @param request The get request
   * @return  A reply containing all documents found
   */
  public Request.GetReply handleRequest(Request.GetRequest request) {
    return dbServer.handleRequest(request);
  }
}
