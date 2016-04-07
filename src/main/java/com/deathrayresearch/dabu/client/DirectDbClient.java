package com.deathrayresearch.dabu.client;

import com.deathrayresearch.dabu.shared.Document;
import com.deathrayresearch.dabu.shared.msg.DocumentGetReply;
import com.deathrayresearch.dabu.shared.msg.DocumentGetRequest;
import com.deathrayresearch.dabu.shared.msg.DocumentWriteRequest;
import com.deathrayresearch.dabu.shared.msg.Reply;
import com.deathrayresearch.dabu.shared.msg.Request;
import com.deathrayresearch.dabu.shared.msg.KeyValueWriteRequest;

/**
 * A database client that is in the same address space (JVM) as the server,
 * and communicates with it by direct message sends
 */
public class DirectDbClient implements DbClient {

  private CommunicationClient communicationClient = new DirectCommunicationClient();

  @Override
  public void writeDoc(Document document) {

    Request request = new DocumentWriteRequest(document);
    Reply reply = communicationClient.sendRequest(request);
  }

  @Override
  public Document getDoc(byte[] key) {
    DocumentGetRequest request = new DocumentGetRequest(key);
    DocumentGetReply reply = communicationClient.sendRequest(request);
    return reply.getDocument();
  }

  @Override
  public void deleteDoc(byte[] ... key) {

  }
}
