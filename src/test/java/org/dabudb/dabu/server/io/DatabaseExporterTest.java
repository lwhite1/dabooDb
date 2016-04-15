package org.dabudb.dabu.server.io;

import com.google.protobuf.ByteString;
import org.dabudb.dabu.server.DbServer;
import org.dabudb.dabu.server.ServerSettings;
import org.dabudb.dabu.shared.protobufs.Request;
import org.junit.Test;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.dabudb.dabu.shared.msg.MessageUtils.*;

/**
 *
 */
public class DatabaseExporterTest {

  private final DbServer server = DbServer.get();

  @Test
  public void testExport() {
    List<Request.Document> documentList = new ArrayList<>();
    Request.Header header = getHeader();
    Request.WriteRequestBody body = getWriteRequestBody(documentList);
    Request.WriteRequest request = getWriteRequest(header, body);
    Request.WriteReply reply = server.handleRequest(request, request.toByteArray());

    Request.GetRequestBody getBody = getGetRequestBody(ByteString.EMPTY);
    Request.GetRequest getRequest = getGetRequest(header, getBody);
    Request.GetReply getReply = server.handleRequest(getRequest);

    server.export(Paths.get(ServerSettings.getInstance().getDatabaseDirectory(),"exports").toFile());
  }
}