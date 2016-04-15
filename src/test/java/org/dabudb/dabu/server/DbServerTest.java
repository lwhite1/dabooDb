package org.dabudb.dabu.server;

import org.dabudb.dabu.generated.protobufs.Request;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.dabudb.dabu.shared.msg.MessageUtils.*;

/**
 *
 */
public class DbServerTest {

  private final DbServer server = DbServer.get();

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