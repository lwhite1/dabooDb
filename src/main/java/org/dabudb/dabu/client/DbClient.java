package org.dabudb.dabu.client;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import org.dabudb.dabu.shared.Document;
import org.dabudb.dabu.shared.DocumentFactory;
import org.dabudb.dabu.shared.protobufs.Request;

import java.util.ArrayList;
import java.util.List;

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

  private final ClientSettings settings = ClientSettings.getInstance();

  private DbClient() {}

  public void write(Document document) {

    Request.Document doc = getDocument(document);
    Header header = getHeader();
    WriteRequestBody body = getWriteRequestBody(doc);
    WriteRequest request = getWriteRequest(header, body);
    Request.WriteReply reply = settings.getCommClient().sendRequest(request);
    checkErrorCondition(reply.getErrorCondition());
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
    checkErrorCondition(reply.getErrorCondition());
  }

  @Override
  public Document get(byte[] key) {
    ByteString keyBytes = ByteString.copyFrom(key);
    Header header = getHeader();
    GetRequestBody body = getGetRequestBody(keyBytes);
    GetRequest request = getGetRequest(header, body);
    Request.GetReply reply = settings.getCommClient().sendRequest(request);
    checkErrorCondition(reply.getErrorCondition());
    ByteString resultBytes = reply.getDocumentBytesList().get(0);
    return getDocumentFromRequestDoc(resultBytes);
  }

  private Document getDocumentFromRequestDoc(ByteString resultBytes) {
    Document document = DocumentFactory.documentForClass(settings.getDocumentClass());

    Request.Document result;
    try {
      result = Request.Document.parseFrom(resultBytes);
    } catch (InvalidProtocolBufferException e) {
      e.printStackTrace();
      throw new RuntimeException("Unable to parse from protobuf");
    }
    document.setContentClass(result.getContentClass());
    document.setContentType(result.getContentType());
    document.setKey(result.getKey().toByteArray());
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
    Request.GetReply reply = settings.getCommClient().sendRequest(request);
    checkErrorCondition(reply.getErrorCondition());
    List<ByteString> byteStringList = reply.getDocumentBytesList();
    List<Document> results = new ArrayList<>();
    for (ByteString bytes : byteStringList) {
      results.add(getDocumentFromRequestDoc(bytes));
    }
    return results;
  }

  @Override
  public void delete(Document document) {
    Request.Document doc = getDocument(document);
    Header header = getHeader();
    DeleteRequestBody body = getDeleteRequestBody(doc);
    DeleteRequest request = getDeleteRequest(header, body);
    Request.DeleteReply reply = settings.getCommClient().sendRequest(request);
    checkErrorCondition(reply.getErrorCondition());
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
    checkErrorCondition(reply.getErrorCondition());
  }

  private void checkErrorCondition(ErrorCondition condition) {
    if (condition != null && condition.getErrorType() != ErrorType.NONE) {
      System.out.println(condition);
      //TODO(lwhite): Proper error handling
      throw new RuntimeException("OOOPS!");
    }
  }
}
