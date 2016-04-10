package org.dabudb.dabu.server;

import org.dabudb.dabu.shared.msg.request.DocDeleteRequest;
import org.dabudb.dabu.shared.msg.request.DocsDeleteRequest;
import org.dabudb.dabu.shared.msg.request.DocsGetRequest;
import org.dabudb.dabu.shared.msg.request.DocsWriteRequest;
import org.dabudb.dabu.shared.msg.request.QueryRequest;
import org.dabudb.dabu.shared.msg.reply.Reply;

/**
 *
 */
public class DirectCommServer implements CommServer {

  private final DbServer dbServer = DbServer.get();

  public Reply handleRequest(DocsWriteRequest request) {
    return dbServer.handleRequest(request);
  }

  /**
   */
  public Reply handleRequest(DocDeleteRequest request) {
    return dbServer.handleRequest(request);
  }

  /**
   */
  public Reply handleRequest(DocsDeleteRequest request) {
    return dbServer.handleRequest(request);
  }

  /**
   */
  public Reply handleRequest(QueryRequest request) {
    return dbServer.handleRequest(request);
  }

  /**
   */
  public Reply handleRequest(DocsGetRequest request) {
    return dbServer.handleRequest(request);
  }
}
