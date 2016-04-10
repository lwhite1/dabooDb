package com.deathrayresearch.dabu.client;

import com.deathrayresearch.dabu.shared.ContentsPipe;
import com.deathrayresearch.dabu.shared.Document;
import com.deathrayresearch.dabu.shared.DocumentContents;
import com.deathrayresearch.dabu.shared.msg.DeleteReply;
import com.deathrayresearch.dabu.shared.msg.DocDeleteRequest;
import com.deathrayresearch.dabu.shared.msg.DocGetReply;
import com.deathrayresearch.dabu.shared.msg.DocGetRequest;
import com.deathrayresearch.dabu.shared.msg.DocWriteRequest;
import com.deathrayresearch.dabu.shared.msg.DocsDeleteRequest;
import com.deathrayresearch.dabu.shared.msg.DocsGetReply;
import com.deathrayresearch.dabu.shared.msg.DocsGetRequest;
import com.deathrayresearch.dabu.shared.msg.DocsWriteRequest;
import com.deathrayresearch.dabu.shared.msg.Reply;
import com.deathrayresearch.dabu.shared.msg.WriteReply;

import java.util.ArrayList;
import java.util.List;

import static com.deathrayresearch.dabu.client.Settings.*;

/**
 *
 */
public class DbClient implements DocumentApi {

  public static DbClient get() {
    return new DbClient();
  }

  private final Settings settings = Settings.getInstance();

  private DbClient() {
  }

  public ContentsPipe getContentsPipe() {
    return settings.getContentsPipe();
  }

  DocumentContents bytesToContents(Class contentClass, byte[] contentBytes) {
    return settings.getContentsPipe().bytesToContents(contentClass, contentBytes);
  }

  Document bytesToDocument(byte[] documentBytes) {
    return settings.getDocumentSerializer().bytesToDocument(settings.getDocumentClass(), documentBytes);
  }

  @Override
  public void writeDoc(Document document) {
    DocWriteRequest request = new DocWriteRequest(document);
    Reply reply = settings.getCommClient().sendRequest(request);
  }

  @Override
  public void writeDocs(List<Document> documentCollection) {
    DocsWriteRequest request = new DocsWriteRequest(documentCollection);
    WriteReply reply = settings.getCommClient().sendRequest(request);
  }

  @Override
  public Document getDoc(byte[] key) {
    DocGetRequest request = new DocGetRequest(key);
    DocGetReply reply = settings.getCommClient().sendRequest(request);
    return bytesToDocument(reply.getDocument());
  }

  @Override
  public List<Document> getDocs(List<byte[]> keys) {
    List<Document> results = new ArrayList<>();
    DocsGetRequest request = new DocsGetRequest(keys);
    DocsGetReply reply = settings.getCommClient().sendRequest(request);
    List<byte[]> docs = reply.getDocuments();
    for (byte[] docBytes : docs) {
      results.add(bytesToDocument(docBytes));
    }
    return results;
  }

  @Override
  public void deleteDoc(byte[] key) {
    DocDeleteRequest request = new DocDeleteRequest(key);
    DeleteReply reply = settings.getCommClient().sendRequest(request);
  }

  @Override
  public void deleteDocs(List<byte[]> keys) {
    DocsDeleteRequest request = new DocsDeleteRequest(keys);
    DeleteReply reply = settings.getCommClient().sendRequest(request);
  }
}
