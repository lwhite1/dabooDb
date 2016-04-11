package org.dabudb.dabu.client;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import org.dabudb.dabu.shared.ContentsPipe;
import org.dabudb.dabu.shared.Document;
import org.dabudb.dabu.shared.DocumentContents;
import org.dabudb.dabu.shared.DocumentFactory;
import org.dabudb.dabu.shared.DocumentUtils;
import org.dabudb.dabu.shared.msg.MessageUtils;
import org.dabudb.dabu.shared.protobufs.Request;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.dabudb.dabu.shared.DocumentUtils.*;
import static org.dabudb.dabu.shared.msg.MessageUtils.*;
import static org.dabudb.dabu.shared.protobufs.Request.*;

/**
 * The database client interface
 */
public class DbClient implements DocumentApi {

  public static DbClient get() {
    return new DbClient();
  }

  private final Settings settings = Settings.getInstance();

  private DbClient() {}

  public ContentsPipe getContentsPipe() {
    return settings.getContentsPipe();
  }

  DocumentContents bytesToContents(Class contentClass, byte[] contentBytes) {
    return settings.getContentsPipe().bytesToContents(contentClass, contentBytes);
  }

  Document bytesToDocument(byte[] documentBytes) {
    return settings.getDocumentSerializer().bytesToDocument(settings.getDocumentClass(), documentBytes);
  }

  public void write(Document document) {

    Request.Document doc = getDocument(document);
    Header header = getHeader();
    WriteRequestBody body = getWriteRequestBody(doc);
    WriteRequest request = getWriteRequest(header, body);
    Request.WriteReply reply = settings.getCommClient().sendRequest(request);
  }

  public void write(List<Document> documentCollection) {
    List<Request.Document> documentList = new ArrayList<>();
    for (Document document : documentCollection) {
      Request.Document doc = getDocument(document);
      documentList.add(doc);
    }
    Header header = getHeader();
    WriteRequestBody body = getWriteRequestBody(documentList);
    WriteRequest request = getWriteRequest(header, body);
    Request.WriteReply reply = settings.getCommClient().sendRequest(request);
  }

  @Override
  public Document get(byte[] key) {
    ByteString keyBytes = ByteString.copyFrom(key);
    Header header = getHeader();
    GetRequestBody body = getGetRequestBody(keyBytes);
    GetRequest request = getGetRequest(header, body);
    Request.GetReply reply = settings.getCommClient().sendRequest(request);
    return getDocumentFromRequestDoc(reply);
  }

  private Document getDocumentFromRequestDoc(GetReply reply) {
    Document document = DocumentFactory.documentForClass(settings.getDocumentClass());

    ByteString resultBytes = reply.getDocumentBytesList().get(0);
    Request.Document result = null;
    try {
      result = Request.Document.parseFrom(resultBytes);
    } catch (InvalidProtocolBufferException e) {
      e.printStackTrace();
      throw new RuntimeException("Unable to parse from protobuf");
    }
    document.setContentClass(result.getContentClass());
    document.setContentType(result.getContentType());
    document.setKey(result.getKey().toByteArray());
    document.setDeleted(result.getDeleted());
    document.setSchemaVersion((short) result.getSchemaVersion());
    document.setInstanceVersion(result.getInstanceVersion());
    document.setContents(result.getContentBytes().toByteArray());
    return document;
  }

  @Override
  public List<Document> get(List<byte[]> keys) {
    Header header = getHeader();
    List<ByteString> byteStrings = new ArrayList<>();
    for (byte[] bytes : keys) {
      byteStrings.add(ByteString.copyFrom(bytes));
    }
    GetRequestBody body = getGetRequestBody(byteStrings);
    GetRequest request = getGetRequest(header, body);

    List<Document> results = new ArrayList<>();
    return results;
  }

  @Override
  public void delete(Document document) {
    Request.Document doc = getDocument(document);
    Header header = getHeader();
    DeleteRequestBody body = getDeleteRequestBody(doc);
    DeleteRequest request = getDeleteRequest(header, body);
    Request.DeleteReply reply = settings.getCommClient().sendRequest(request);
  }

  @Override
  public void delete(List<Document> documents) {
    List<Request.Document> docs = new ArrayList<>();

    for (Document document : documents) {
      Request.Document doc = getDocument(document);
      docs.add(doc);
    }
    Header header = getHeader();
    DeleteRequestBody body = getDeleteRequestBody(docs);
    DeleteRequest request = getDeleteRequest(header, body);
    Request.DeleteReply reply = settings.getCommClient().sendRequest(request);
  }
}
