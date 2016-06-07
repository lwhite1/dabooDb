package org.daboodb.daboo.client;

import com.google.protobuf.ByteString;
import org.daboodb.daboo.generated.protobufs.Request;
import org.daboodb.daboo.shared.Document;
import org.daboodb.daboo.shared.DocumentUtils;
import org.daboodb.daboo.testutil.BasicTest;
import org.daboodb.daboo.testutil.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.daboodb.daboo.shared.DocumentUtils.getDocumentFromRequestDoc;
import static org.daboodb.daboo.shared.RequestUtils.*;
import static org.daboodb.daboo.shared.RequestUtils.getWriteHeader;
import static org.junit.Assert.assertNotNull;

/**
 *  Tests for Http-based database client
 */
public class HttpCommClientTest extends BasicTest {

  private static final HttpCommClient client = new HttpCommClient("localhost", 7070);

  public static void main(String[] args) {
    // Create some data and process the write
    List<Request.Document> documentList = new ArrayList<>();

    Person person = Person.createPeoples(1).get(0);
    documentList.add(DocumentUtils.getDocument(person));

    Request.Header header = getWriteHeader();
    Request.WriteRequestBody body = getWriteRequestBody(documentList);
    Request.WriteRequest writeRequest = getWriteRequest(header, body);

    Request.WriteReply reply = client.sendRequest(writeRequest);
    assertNotNull(reply);

    Request.GetRequestBody getRequestBody = getGetRequestBody(ByteString.copyFrom(person.getKey()));
    Request.GetRequest getRequest = getGetRequest(getGetHeader(), getRequestBody);

    Request.GetReply reply1 = client.sendRequest(getRequest);
    assertNotNull(reply1);
    ByteString docBytes = reply1.getDocumentBytes(0);
    Document document = getDocumentFromRequestDoc(docBytes);
    System.out.println(document);
  }
}