package org.dabudb.dabu.client;

import org.dabudb.dabu.shared.ContentsPipe;
import org.dabudb.dabu.shared.Document;
import org.dabudb.dabu.shared.DocumentContents;
import org.dabudb.dabu.shared.msg.reply.DeleteReply;
import org.dabudb.dabu.shared.msg.request.DocDeleteRequest;
import org.dabudb.dabu.shared.msg.reply.DocGetReply;
import org.dabudb.dabu.shared.msg.request.DocGetRequest;
import org.dabudb.dabu.shared.msg.request.DocWriteRequest;
import org.dabudb.dabu.shared.msg.request.DocsDeleteRequest;
import org.dabudb.dabu.shared.msg.reply.DocsGetReply;
import org.dabudb.dabu.shared.msg.request.DocsGetRequest;
import org.dabudb.dabu.shared.msg.request.DocsWriteRequest;
import org.dabudb.dabu.shared.msg.reply.Reply;
import org.dabudb.dabu.shared.msg.reply.WriteReply;

import java.util.ArrayList;
import java.util.List;

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
