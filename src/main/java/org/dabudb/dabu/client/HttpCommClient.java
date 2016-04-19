package org.dabudb.dabu.client;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.InvalidProtocolBufferException;
import org.dabudb.dabu.client.exceptions.RuntimeSerializationException;
import org.dabudb.dabu.generated.protobufs.Request;
import org.dabudb.dabu.server.DirectCommServer;
import org.dabudb.dabu.shared.exceptions.RuntimeDatastoreException;

import java.io.IOException;
import java.io.InputStream;

/**
 * A communication client that communicates directly with an in-process db server
 */
public class HttpCommClient implements CommClient {

  // We send raw bytes for performance reasons
  private static final String CONTENT_TYPE = "application/octet-stream";

  //TODO(lwhite) Replace this with configurable address
  private static final String serverAddress = "localhost:7070/write";

  HttpCommClient() {
  }

  @Override
  public Request.WriteReply sendRequest(Request.WriteRequest request) {

    GenericUrl url = new GenericUrl(serverAddress);

    HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
    try {
      HttpContent content = new ByteArrayContent(CONTENT_TYPE, request.toByteArray());
      HttpRequest httpRequest = requestFactory.buildPostRequest(url, content);
      HttpResponse response = httpRequest.execute();
      InputStream is = response.getContent();
      CodedInputStream cis = CodedInputStream.newInstance(is);
      Request.WriteReply reply = Request.WriteReply.parseFrom(cis);
      is.close();
      return reply;
    } catch (InvalidProtocolBufferException e) {
      throw new RuntimeSerializationException("ProtocolBuffer serialization exception caught.", e);
    } catch (IOException e) {
      throw new RuntimeDatastoreException("IO exception caught making HTTP request.", e);
    }
  }

  @Override
  public Request.GetReply sendRequest(Request.GetRequest request) {
    return null;
  }

  @Override
  public Request.GetReply sendRequest(Request.GetRangeRequest request) {
    return null;
  }
}
