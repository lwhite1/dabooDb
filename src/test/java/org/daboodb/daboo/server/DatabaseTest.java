package org.daboodb.daboo.server;

import com.google.protobuf.ByteString;
import org.daboodb.daboo.generated.protobufs.Request;
import org.daboodb.daboo.server.io.WriteAheadLog;
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

import static org.daboodb.daboo.shared.RequestUtils.*;
import static org.junit.Assert.*;

/**
 * Tests for Database
 */
public class DatabaseTest extends BasicTest {

  private Database server;

  @Override
  @Before
  public void setUp() throws Exception {
    super.tearDown();
    WriteAheadLog log = ServerSettings.getInstance().getWriteAheadLog();
    log.clear();
    server = Database.get();
    server.clear();
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

    // Create some data and process the write
    List<Request.Document> documentList = new ArrayList<>();

    Person person = Person.createPeoples(1).get(0);
    documentList.add(DocumentUtils.getDocument(person));

    Request.Header header = getWriteHeader();
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
    Document result = DocumentUtils.getDocumentFromRequestDoc(bytes);
    Person person1 = (Person) result;
    assertEquals(person, person1);
  }

  @Ignore
  @Test
  public void testOptimisticLocking() {

    // Create an new person and write it to the db
    List<Request.Document> documentList = new ArrayList<>();
    Person person = Person.createPeoples(1).get(0);
    documentList.add(DocumentUtils.getDocument(person));

    Request.Header header = getWriteHeader();
    Request.WriteRequestBody body = getWriteRequestBody(documentList);
    Request.WriteRequest writeRequest = getWriteRequest(header, body);
    server.handleRequest(writeRequest, writeRequest.toByteArray());

    // Create a Get request and read back the data we just wrote
    Request.GetRequestBody getRequestBody = getGetRequestBody(ByteString.copyFrom(person.getKey()));
    Request.GetRequest getRequest = getGetRequest(header, getRequestBody);

    Request.GetReply reply = server.handleRequest(getRequest);
    ByteString bytes = reply.getDocumentBytesList().get(0);
    Document result = DocumentUtils.getDocumentFromRequestDoc(bytes);
    assertEquals(1, result.instanceVersion());

    // resave the result

    documentList.clear();
    documentList.add(DocumentUtils.getDocument(result));
    body = getWriteRequestBody(documentList);
    writeRequest = getWriteRequest(header, body);
    server.handleRequest(writeRequest, writeRequest.toByteArray());

    // Create a Get request and read back the data we just wrote
    getRequestBody = getGetRequestBody(ByteString.copyFrom(person.getKey()));
    getRequest = getGetRequest(header, getRequestBody);

    reply = server.handleRequest(getRequest);
    bytes = reply.getDocumentBytesList().get(0);
    Document result2 = DocumentUtils.getDocumentFromRequestDoc(bytes);
    assertEquals(2, result.instanceVersion());

    // now we should fail, writing the earlier version
    documentList.clear();
    documentList.add(DocumentUtils.getDocument(result));
    body = getWriteRequestBody(documentList);
    writeRequest = getWriteRequest(header, body);
    server.handleRequest(writeRequest, writeRequest.toByteArray());
  }
}