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
    if (!super.equals(o)) return false;
    DocWriteRequest that = (DocWriteRequest) o;
    return Arrays.equals(getKey(), that.getKey()) &&
        Arrays.equals(getDocumentBytes(), that.getDocumentBytes());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getKey(), getDocumentBytes());
  }

  @Override
  public String toString() {
    return "DocWriteRequest{" +
        "key=" + Arrays.toString(key) +
        ", documentBytes=" + Arrays.toString(documentBytes) +
        "} " + super.toString();
  }
}
