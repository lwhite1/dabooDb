package org.dabudb.dabu.shared.msg.request;

import javax.annotation.concurrent.Immutable;
import java.util.Arrays;
import java.util.Objects;

/**
 * A request to delete a single document
 */
@Immutable
public final class DocDeleteRequest extends AbstractRequest {

  private final byte[] key;

  public DocDeleteRequest(byte[] key) {
    super(RequestType.DOCUMENT_DELETE);
    this.key = key;
  }

  public byte[] getKey() {
    return key;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    DocDeleteRequest that = (DocDeleteRequest) o;
    return Arrays.equals(getKey(), that.getKey());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getKey());
  }

  @Override
  public String toString() {
    return "DocDeleteRequest{" +
        "key=" + Arrays.toString(key) +
        "} " + super.toString();
  }
}
