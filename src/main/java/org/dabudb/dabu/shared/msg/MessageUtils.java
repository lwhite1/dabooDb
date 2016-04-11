package org.dabudb.dabu.shared.msg;

import com.google.protobuf.ByteString;
import org.dabudb.dabu.shared.protobufs.Request;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 *
 */
public final class MessageUtils {

  private MessageUtils() {}

  public static Request.WriteRequest getWriteRequest(Request.Header header, Request.WriteRequestBody body) {
    return Request.WriteRequest.newBuilder()
        .setHeader(header)
        .setBody(body)
        .build();
  }

  public static Request.DeleteRequest getDeleteRequest(Request.Header header, Request.DeleteRequestBody body) {
    return Request.DeleteRequest.newBuilder()
        .setHeader(header)
        .setBody(body)
        .build();
  }

  public static Request.GetRequest getGetRequest(Request.Header header, Request.GetRequestBody body) {
    return Request.GetRequest.newBuilder()
        .setHeader(header)
        .setBody(body)
        .build();
  }

  public static Request.WriteRequestBody getWriteRequestBody(Request.Document doc) {
    return Request.WriteRequestBody.newBuilder()
        .addAllDocument(Collections.singletonList(doc))
        .build();
  }

  public static Request.DeleteRequestBody getDeleteRequestBody(Request.Document doc) {
    return Request.DeleteRequestBody.newBuilder()
        .addAllDocument(Collections.singletonList(doc))
        .build();
  }

  public static Request.GetRequestBody getGetRequestBody(ByteString key) {
    return Request.GetRequestBody.newBuilder()
        .addAllKey(Collections.singletonList(key))
        .build();
  }

  public static Request.GetRequestBody getGetRequestBody(List<ByteString> keys) {
    return Request.GetRequestBody.newBuilder()
        .addAllKey(keys)
        .build();
  }

  public static Request.WriteRequestBody getWriteRequestBody(List<Request.Document> docs) {
    return Request.WriteRequestBody.newBuilder()
        .addAllDocument(docs)
        .build();
  }

  public static Request.DeleteRequestBody getDeleteRequestBody(List<Request.Document> docs) {
    return Request.DeleteRequestBody.newBuilder()
        .addAllDocument(docs)
        .build();
  }

  public static Request.Header getHeader() {
    return Request.Header.newBuilder()
        .setId(ByteString.copyFrom(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8)))
        .setRequestType(Request.RequestType.WRITE)
        .setTimestamp(Instant.now().toEpochMilli())
        .build();
  }

}