package com.deathrayresearch.dabu.client;

import com.deathrayresearch.dabu.server.DbServer;
import com.deathrayresearch.dabu.shared.Document;
import com.deathrayresearch.dabu.shared.msg.GetReply;
import com.deathrayresearch.dabu.shared.msg.GetRequest;
import com.deathrayresearch.dabu.shared.msg.WriteRequest;
import com.deathrayresearch.dabu.shared.msg.Reply;
import com.deathrayresearch.dabu.shared.msg.Request;

import java.nio.charset.StandardCharsets;

/**
 * A database client that is in the same address space (JVM) as the server,
 * and communicates with it by direct message sends
 */
public class DirectDbClient implements DbClient {

  private CommunicationClient communicationClient = new DirectCommunicationClient();

  @Override
  public void writeDoc(Document document) {

    Request request = new WriteRequest(document);
    Reply reply = communicationClient.sendRequest(request);
  }

  @Override
  public Document getDoc(byte[] key) {
    GetRequest request = new GetRequest(key);
    GetReply reply = communicationClient.sendRequest(request);
    String docString = new String(reply.getDocument(), StandardCharsets.UTF_8);
    return (Document) Document.GSON.fromJson(docString, DbServer.INSTANCE.getDocumentClass());
  }

  @Override
  public void deleteDoc(byte[] ... key) {

  }
}
