package org.dabudb.dabu.shared.msg.request;

import javax.annotation.concurrent.Immutable;
import java.util.Arrays;
import java.util.Objects;

/**
 * A request to get a single document
 */
@Immutable
public final class DocGetRequest extends AbstractRequest {

  private final byte[] key;

  public DocGetRequest(byte[] key) {
    super(RequestType.DOCUMENT_GET);
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
    DocGetRequest that = (DocGetRequest) o;
    return Arrays.equals(getKey(), that.getKey());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getKey());
  }

  @Override
  public String toString() {
    return "DocGetRequest{" +
        "key=" + Arrays.toString(key) +
        "} " + super.toString();
  }
}
