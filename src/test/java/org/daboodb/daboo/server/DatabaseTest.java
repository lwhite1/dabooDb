package org.daboodb.daboo.server;

import com.google.protobuf.ByteString;
import org.daboodb.daboo.generated.protobufs.Request;
import org.daboodb.daboo.server.io.WriteAheadLog;
import org.daboodb.daboo.shared.Document;
import org.daboodb.daboo.shared.DocumentUtils;
import org.daboodb.daboo.shared.StandardDocument;
import org.daboodb.daboo.testutil.BasicTest;
import org.daboodb.daboo.testutil.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.daboodb.daboo.shared.RequestUtils.*;
import static org.junit.Assert.*;

/**
 * Tests for Database
 */
public class DatabaseTest extends BasicTest {

  @Override
  @Before
  public void setUp() throws Exception {
    super.tearDown();
    WriteAheadLog log = ServerSettings.getInstance().getWriteAheadLog();
    log.clear();
  }

  @Override
  @After
  public void tearDown() throws Exception {
    WriteAheadLog log = ServerSettings.getInstance().getWriteAheadLog();
    log.clear();

    super.tearDown();
  }

  @Test
  public void testHandleRequests() {

    Database server = Database.get();
    server.clear();

    // Create some data and process the write
    List<Request.Document> documentList = new ArrayList<>();

    Person person = Person.createPeoples(1).get(0);
    StandardDocument standardDocument = new StandardDocument(person);
    documentList.add(DocumentUtils.getDocument(standardDocument));

    Request.Header header = getHeader();
    Request.WriteRequestBody body = getWriteRequestBody(documentList);
    Request.WriteRequest writeRequest = getWriteRequest(header, body);

    assertTrue(server.isEmpty());
    server.handleRequest(writeRequest, writeRequest.toByteArray());

    // verify that the write happened
    assertFalse(server.isEmpty());
    assertEquals(1, server.size());

    // Create a Get request and read back the data we just wrote
    Request.GetRequestBody getRequestBody = getGetRequestBody(ByteString.copyFrom(person.getKey()));
    Request.GetRequest getRequest = getGetRequest(header, getRequestBody);

    Request.GetReply reply = server.handleRequest(getRequest);
    ByteString bytes = reply.getDocumentBytesList().get(0);
    Document result = DocumentUtils.getDocumentFromRequestDoc(StandardDocument.class, bytes);
    Person person1 = (Person) result.documentContents();
    assertEquals(person, person1);
  }

  @Test
  public void testOptimisticLocking() {
    Database server = Database.get();
    server.clear();

    // Create some data and process the write
    List<Request.Document> documentList = new ArrayList<>();

    Person person = Person.createPeoples(1).get(0);
    StandardDocument standardDocument = new StandardDocument(person);
    documentList.add(DocumentUtils.getDocument(standardDocument));

    Request.Header header = getHeader();
    Request.WriteRequestBody body = getWriteRequestBody(documentList);
    Request.WriteRequest writeRequest = getWriteRequest(header, body);

    assertTrue(server.isEmpty());
    server.handleRequest(writeRequest, writeRequest.toByteArray());

    // verify that the write happened
    assertFalse(server.isEmpty());
    assertEquals(1, server.size());

    // Create a Get request and read back the data we just wrote
    Request.GetRequestBody getRequestBody = getGetRequestBody(ByteString.copyFrom(person.getKey()));
    Request.GetRequest getRequest = getGetRequest(header, getRequestBody);

    Request.GetReply reply = server.handleRequest(getRequest);
    ByteString bytes = reply.getDocumentBytesList().get(0);
    Document result = DocumentUtils.getDocumentFromRequestDoc(StandardDocument.class, bytes);
    Person person1 = (Person) result.documentContents();
    assertEquals(person, person1);
  }
}