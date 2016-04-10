package org.dabudb.dabu.client;

import org.dabudb.dabu.server.DirectCommServer;
import org.dabudb.dabu.shared.msg.reply.DeleteReply;
import org.dabudb.dabu.shared.msg.request.DocDeleteRequest;
import org.dabudb.dabu.shared.msg.request.DocsDeleteRequest;
import org.dabudb.dabu.shared.msg.reply.DocsGetReply;
import org.dabudb.dabu.shared.msg.request.DocsGetRequest;
import org.dabudb.dabu.shared.msg.request.DocsWriteRequest;
import org.dabudb.dabu.shared.msg.reply.DocGetReply;
import org.dabudb.dabu.shared.msg.reply.WriteReply;

/**
 * A communication client that communicates directly with an in-process db server
 */
public class DirectCommClient implements CommClient {

  private final DirectCommServer directCommServer;

  public DirectCommClient() {
    directCommServer = new DirectCommServer();
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
  public WriteReply sendRequest(DocsWriteRequest request) {
    return (WriteReply) directCommServer.handleRequest(request);
  }
}
