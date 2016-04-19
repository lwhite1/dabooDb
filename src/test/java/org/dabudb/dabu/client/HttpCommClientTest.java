package org.dabudb.dabu.client;

import org.dabudb.dabu.generated.protobufs.Request;
import org.dabudb.dabu.shared.DocumentUtils;
import org.dabudb.dabu.shared.StandardDocument;
import org.dabudb.dabu.testutil.BasicTest;
import org.dabudb.dabu.testutil.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.dabudb.dabu.shared.RequestUtils.*;
import static org.junit.Assert.*;

/**
 *  Tests for Http-based database client
 */
public class HttpCommClientTest extends BasicTest {

  HttpCommClient client = new HttpCommClient();

  @Override
  @Before
  public void setUp() throws Exception {
    super.tearDown();
  }

  @After
  public void tearDown() throws Exception {
    super.tearDown();
  }

  @Ignore
  @Test
  public void testSendWriteRequest() {
    // Create some data and process the write
    List<Request.Document> documentList = new ArrayList<>();

    Person person = Person.createPeoples(1).get(0);
    StandardDocument standardDocument = new StandardDocument(person);
    documentList.add(DocumentUtils.getDocument(standardDocument));

    Request.Header header = getHeader();
    Request.WriteRequestBody body = getWriteRequestBody(documentList);
    Request.WriteRequest writeRequest = getWriteRequest(header, body);

    Request.WriteReply reply = client.sendRequest(writeRequest);
  }

  @Ignore
  @Test
  public void testSendRequest1() {

  }

  @Ignore
  @Test
  public void testSendRequest2() {

  }
}