package com.deathrayresearch.dabu.client;

import com.deathrayresearch.dabu.shared.Document;
import com.deathrayresearch.dabu.shared.msg.GetReply;
import com.deathrayresearch.dabu.shared.msg.GetRequest;
import com.deathrayresearch.dabu.shared.msg.WriteRequest;
import com.deathrayresearch.dabu.shared.msg.Reply;
import com.deathrayresearch.dabu.shared.msg.Request;

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
    return reply.getDocument();
  }

  @Override
  public void deleteDoc(byte[] ... key) {

  }
}
