package com.deathrayresearch.dabu.client;

import com.deathrayresearch.dabu.server.DbServer;
import com.deathrayresearch.dabu.shared.Document;
import com.deathrayresearch.dabu.shared.msg.DeleteReply;
import com.deathrayresearch.dabu.shared.msg.DocDeleteRequest;
import com.deathrayresearch.dabu.shared.msg.DocsDeleteRequest;
import com.deathrayresearch.dabu.shared.msg.DocsGetReply;
import com.deathrayresearch.dabu.shared.msg.DocsGetRequest;
import com.deathrayresearch.dabu.shared.msg.DocGetReply;
import com.deathrayresearch.dabu.shared.msg.DocGetRequest;
import com.deathrayresearch.dabu.shared.msg.DocWriteRequest;
import com.deathrayresearch.dabu.shared.msg.DocsWriteRequest;
import com.deathrayresearch.dabu.shared.msg.Reply;
import com.deathrayresearch.dabu.shared.msg.WriteReply;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
  public void writeDocs(List<Document> documentCollection) {
    DocsWriteRequest request = new DocsWriteRequest(documentCollection);
    WriteReply reply = commClient.sendRequest(request);
  }

  @Override
  public Document getDoc(byte[] key) {
    DocGetRequest request = new DocGetRequest(key);
    DocGetReply reply = commClient.sendRequest(request);
    String docString = new String(reply.getDocument(), StandardCharsets.UTF_8);
    return (Document) Document.GSON.fromJson(docString, DbServer.INSTANCE.getDocumentClass());
  }

  @Override
  public List<Document> getDocs(List<byte[]> keys) {
    List<Document> results = new ArrayList<>();
    DocsGetRequest request = new DocsGetRequest(keys);
    DocsGetReply reply = commClient.sendRequest(request);
    List<byte[]> docs = reply.getDocuments();
    for (byte[] docBytes : docs) {
      String docString = new String(docBytes, StandardCharsets.UTF_8);
      results.add((Document) Document.GSON.fromJson(docString, DbServer.INSTANCE.getDocumentClass()));
    }
    return results;
  }

  @Override
  public void deleteDoc(byte[] key) {
    DocDeleteRequest request = new DocDeleteRequest(key);
    DeleteReply reply = commClient.sendRequest(request);
  }

  @Override
  public void deleteDocs(List<byte[]> keys) {
    DocsDeleteRequest request = new DocsDeleteRequest(keys);
    DeleteReply reply = commClient.sendRequest(request);
  }
}
