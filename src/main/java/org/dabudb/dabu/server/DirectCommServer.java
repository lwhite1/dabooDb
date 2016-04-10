package org.dabudb.dabu.server;

import org.dabudb.dabu.shared.msg.DocDeleteRequest;
import org.dabudb.dabu.shared.msg.DocGetRequest;
import org.dabudb.dabu.shared.msg.DocWriteRequest;
import org.dabudb.dabu.shared.msg.DocsDeleteRequest;
import org.dabudb.dabu.shared.msg.DocsGetRequest;
import org.dabudb.dabu.shared.msg.DocsWriteRequest;
import org.dabudb.dabu.shared.msg.QueryRequest;
import org.dabudb.dabu.shared.msg.Reply;

/**
 *
 */
public class DirectCommServer implements CommServer {

  private final DbServer dbServer = DbServer.get();

  public Reply handleRequest(DocsWriteRequest request) {
    return dbServer.handleRequest(request);
  }

  public Reply handleRequest(DocWriteRequest request) {
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
  public Reply handleRequest(DocGetRequest request) {
    return dbServer.handleRequest(request);
  }

  public Reply handleRequest(DocsGetRequest request) {
    return dbServer.handleRequest(request);
  }
}
