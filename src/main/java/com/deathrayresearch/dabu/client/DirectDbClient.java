package com.deathrayresearch.dabu.client;

import com.deathrayresearch.dabu.shared.Document;
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
  public void write(byte[] key, byte[] value) {
    Request request = new KeyValueWriteRequest(key, value);
    Reply reply = communicationClient.sendRequest(request);
  }

  @Override
  public void write(Document ... document) {
    //Request request = new KeyValueWriteRequest(document);
    //Reply reply = communicationClient.sendRequest(request);
  }

  @Override
  public Document getDoc(byte[] ... key) {
    return null;
  }

  @Override
  public void deleteDoc(byte[] ... key) {

  }

  @Override
  public byte[] get(byte[] ... key) {
    return new byte[0];
  }

  @Override
  public void delete(byte[] ... key) {

  }
}
