package com.deathrayresearch.dabu.server;

import com.deathrayresearch.dabu.shared.msg.DocDeleteRequest;
import com.deathrayresearch.dabu.shared.msg.DocGetRequest;
import com.deathrayresearch.dabu.shared.msg.DocWriteRequest;
import com.deathrayresearch.dabu.shared.msg.DocsDeleteRequest;
import com.deathrayresearch.dabu.shared.msg.DocsGetRequest;
import com.deathrayresearch.dabu.shared.msg.DocsWriteRequest;
import com.deathrayresearch.dabu.shared.msg.QueryRequest;
import com.deathrayresearch.dabu.shared.msg.Reply;

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
