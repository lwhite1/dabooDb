package org.dabudb.dabu.shared.msg.request;

import org.dabudb.dabu.shared.Document;

import java.util.Arrays;
import java.util.Objects;

/**
 * A request to write a single document
 */
public class DocWriteRequest extends AbstractRequest {

  private final byte[] key;
  private final byte[] documentBytes;

  public DocWriteRequest(Document document) {
    super(RequestType.DOCUMENT_WRITE);
    this.key = document.key();
    this.documentBytes = document.marshall();
  }

  public byte[] getKey() {
    return key;
  }

  public byte[] getDocumentBytes() {
    return documentBytes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DocWriteRequest that = (DocWriteRequest) o;
    return Arrays.equals(key, that.key) &&
        Arrays.equals(documentBytes, that.documentBytes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(key, documentBytes);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("DocWriteRequest{");
    sb.append("key=").append(Arrays.toString(key));
    sb.append(", documentBytes=").append(Arrays.toString(documentBytes));
    sb.append('}');
    return sb.toString();
  }
}
