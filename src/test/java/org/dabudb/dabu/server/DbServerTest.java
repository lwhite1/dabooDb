package org.dabudb.dabu.server;

import org.dabudb.dabu.generated.protobufs.Request;
import org.dabudb.dabu.testutil.BasicTest;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.dabudb.dabu.shared.msg.MessageUtils.*;

/**
 * Tests for DbServer
 */
public class DbServerTest extends BasicTest {

  private final DbServer server = DbServer.get();

  @Override
  @After
  public void tearDown() throws Exception {
    super.tearDown();
  }

  @Test
  public void testGet() {

  }

  @Test
  public void testHandleRequest() {
    List<Request.Document> documentList = new ArrayList<>();
    Request.Header header = getHeader();
    Request.WriteRequestBody body = getWriteRequestBody(documentList);
    Request.WriteRequest request = getWriteRequest(header, body);

    server.handleRequest(request, request.toByteArray());
  }

  @Test
  public void testHandleRequest1() {

  }
}