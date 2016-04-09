package com.deathrayresearch.dabu.client;

import com.deathrayresearch.dabu.server.DbServer;
import com.deathrayresearch.dabu.shared.Document;
import com.deathrayresearch.dabu.shared.msg.DeleteReply;
import com.deathrayresearch.dabu.shared.msg.DocDeleteRequest;
import com.deathrayresearch.dabu.shared.msg.GetReply;
import com.deathrayresearch.dabu.shared.msg.DocGetRequest;
import com.deathrayresearch.dabu.shared.msg.DocWriteRequest;
import com.deathrayresearch.dabu.shared.msg.Reply;
import com.deathrayresearch.dabu.shared.msg.Request;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

/**
 * A database client that is in the same address space (JVM) as the server,
 * and communicates with it by direct message sends
 */
public class DirectDbClient implements DbClient {

  private CommClient commClient = new DirectCommClient();

  @Override
  public void writeDoc(Document document) {

    DocWriteRequest request = new DocWriteRequest(document);
    Reply reply = commClient.sendRequest(request);
  }

  @Override
  public void writeDocs(Collection<Document> documentCollection) {

  }

  @Override
  public Document getDoc(byte[] key) {
    DocGetRequest request = new DocGetRequest(key);
    GetReply reply = commClient.sendRequest(request);
    String docString = new String(reply.getDocument(), StandardCharsets.UTF_8);
    return (Document) Document.GSON.fromJson(docString, DbServer.INSTANCE.getDocumentClass());
  }

  @Override
  public List<Document> getDocs(Collection<byte[]> keys) {
    return null;
  }


  @Override
  public void deleteDoc(byte[] key) {
    DocDeleteRequest request = new DocDeleteRequest(key);
    DeleteReply reply = commClient.sendRequest(request);

  }

  @Override
  public void deleteDocs(Collection<byte[]> keys) {

  }
}
