package org.dabudb.dabu.client;

import org.dabudb.dabu.server.DirectCommServer;
import org.dabudb.dabu.shared.msg.DeleteReply;
import org.dabudb.dabu.shared.msg.DocDeleteRequest;
import org.dabudb.dabu.shared.msg.DocWriteRequest;
import org.dabudb.dabu.shared.msg.DocsDeleteRequest;
import org.dabudb.dabu.shared.msg.DocsGetReply;
import org.dabudb.dabu.shared.msg.DocsGetRequest;
import org.dabudb.dabu.shared.msg.DocsWriteRequest;
import org.dabudb.dabu.shared.msg.DocGetReply;
import org.dabudb.dabu.shared.msg.DocGetRequest;
import org.dabudb.dabu.shared.msg.WriteReply;

/**
 * A communication client that communicates directly with an in-process db server
 */
public class DirectCommClient implements CommClient {

  private final DirectCommServer directCommServer;

  public DirectCommClient() {
    directCommServer = new DirectCommServer();
  }

  @Override
  public DocGetReply sendRequest(DocGetRequest request) {
    return (DocGetReply) directCommServer.handleRequest(request);
  }

  @Override
  public DocsGetReply sendRequest(DocsGetRequest request) {
    return (DocsGetReply) directCommServer.handleRequest(request);
  }

  @Override
  public DeleteReply sendRequest(DocDeleteRequest request) {
    return (DeleteReply) directCommServer.handleRequest(request);
  }

  @Override
  public DeleteReply sendRequest(DocsDeleteRequest request) {
    return (DeleteReply) directCommServer.handleRequest(request);
  }

  @Override
  public WriteReply sendRequest(DocWriteRequest request) {
    return (WriteReply) directCommServer.handleRequest(request);
  }

  @Override
  public WriteReply sendRequest(DocsWriteRequest request) {
    return (WriteReply) directCommServer.handleRequest(request);
  }
}
