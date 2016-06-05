package org.daboodb.daboo.client;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.InvalidProtocolBufferException;
import org.daboodb.daboo.client.exceptions.RuntimeSerializationException;
import org.daboodb.daboo.generated.protobufs.Request;
import org.daboodb.daboo.shared.exceptions.RuntimeDatastoreException;

import java.io.IOException;
import java.io.InputStream;

/**
 * A communication client that communicates directly with an in-process db server
 */
public class HttpCommClient implements CommClient {

  // We send raw bytes for performance reasons
  private static final String CONTENT_TYPE = "application/octet-stream";

  private static final String WRITE = "WRITE";
  private static final String GET_RANGE = "GET_RANGE";
  private static final String GET = "GET";

  private final GenericUrl WRITE_URL;
  private final GenericUrl GET_URL;
  private final GenericUrl GET_RANGE_URL;

  HttpCommClient(String address, int port) {
    String serverAddress = new StringBuilder("http://")
        .append(address)
        .append(':')
        .append(port)
        .append('/')
        .toString();
    WRITE_URL = new GenericUrl(serverAddress.concat(WRITE));
    GET_URL = new GenericUrl(serverAddress.concat(GET));
    GET_RANGE_URL = new GenericUrl(serverAddress.concat(GET_RANGE));
  }

  @Override
  public Request.WriteReply sendRequest(Request.WriteRequest request) {

    HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
    try {
      HttpContent content = new ByteArrayContent(CONTENT_TYPE, request.toByteArray());
      HttpRequest httpRequest = requestFactory.buildPostRequest(WRITE_URL, content);
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

  // TODO(lwhite): Implement next two methods
  @Override
  public Request.GetReply sendRequest(Request.GetRequest request) {
    HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
    try {
      HttpContent content = new ByteArrayContent(CONTENT_TYPE, request.toByteArray());
      HttpRequest httpRequest = requestFactory.buildPostRequest(GET_URL, content);
      HttpResponse response = httpRequest.execute();
      InputStream is = response.getContent();
      CodedInputStream cis = CodedInputStream.newInstance(is);
      Request.GetReply reply = Request.GetReply.parseFrom(cis);
      is.close();
      return reply;
    } catch (InvalidProtocolBufferException e) {
      throw new RuntimeSerializationException("ProtocolBuffer serialization exception caught.", e);
    } catch (IOException e) {
      throw new RuntimeDatastoreException("IO exception caught making HTTP request.", e);
    }
  }

  @Override
  public Request.GetReply sendRequest(Request.GetRangeRequest request) {
    return null;
  }
}
