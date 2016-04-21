package org.daboodb.daboo.shared;

import com.google.protobuf.ByteString;
import org.daboodb.daboo.generated.protobufs.Request;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Utilities for creating Request protobufs
 */
public final class RequestUtils {

  private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

  private RequestUtils() {
  }

  public static Request.WriteRequest getWriteRequest(Request.Header header, Request.WriteRequestBody body) {
    return Request.WriteRequest.newBuilder()
        .setHeader(header)
        .setIsDelete(false)
        .setWriteBody(body)
        .build();
  }

  public static Request.WriteRequest getDeleteRequest(Request.Header header, Request.DeleteRequestBody body) {
    return Request.WriteRequest.newBuilder()
        .setHeader(header)
        .setIsDelete(true)
        .setDeleteBody(body)
        .build();
  }

  public static Request.GetRequest getGetRequest(Request.Header header, Request.GetRequestBody body) {
    return Request.GetRequest.newBuilder()
        .setHeader(header)
        .setBody(body)
        .build();
  }

  public static Request.GetRangeRequest getGetRangeRequest(Request.Header header, Request.GetRangeRequestBody body) {
    return Request.GetRangeRequest.newBuilder()
        .setHeader(header)
        .setBody(body)
        .build();
  }

  public static Request.WriteRequestBody getWriteRequestBody(Request.Document doc) {

    Request.DocumentKeyValue keyValue = Request.DocumentKeyValue.newBuilder()
        .setKey(doc.getKey())
        .setValue(doc.toByteString())
        .build();

    return Request.WriteRequestBody.newBuilder()
        .addDocumentKeyValue(keyValue)
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

  public static Request.GetRangeRequestBody getGetRangeRequestBody(ByteString startKey, ByteString endKey) {
    return Request.GetRangeRequestBody.newBuilder()
        .setStartKey(startKey)
        .setEndKey(endKey)
        .build();
  }

  public static Request.WriteRequestBody getWriteRequestBody(List<Request.Document> docs) {

    ArrayList<Request.DocumentKeyValue> keyValueList = new ArrayList<>();

    Set<ConstraintViolation<Request.Document>> validationIssues = new HashSet<>();
    Validator validator = factory.getValidator();

    for (Request.Document doc : docs) {

      // validate according to annotations
      Set<ConstraintViolation<Request.Document>> results = validator.validate(doc);
      validationIssues.addAll(results);

      Request.DocumentKeyValue keyValue = Request.DocumentKeyValue.newBuilder()
          .setKey(doc.getKey())
          .setValue(doc.toByteString())
          .build();
      keyValueList.add(keyValue);
    }

    if (validationIssues.size() > 0) {
      throw new ConstraintViolationException(validationIssues);
    }

    return Request.WriteRequestBody.newBuilder()
        .addAllDocumentKeyValue(keyValueList)
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
