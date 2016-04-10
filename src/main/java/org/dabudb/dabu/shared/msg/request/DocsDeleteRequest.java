package org.dabudb.dabu.shared.msg.request;

import javax.annotation.concurrent.Immutable;
import java.util.List;
import java.util.Objects;

/**
 * A request to delete a collection of documents
 */
@Immutable
public final class DocsDeleteRequest extends AbstractRequest {

  private final List<byte[]> keys;

  public DocsDeleteRequest(List<byte[]> keys) {
    super(RequestType.DELETE);
    this.keys = keys;
  }

  public List<byte[]> getKeys() {
    return keys;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    DocsDeleteRequest that = (DocsDeleteRequest) o;
    return Objects.equals(getKeys(), that.getKeys());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getKeys());
  }

  @Override
  public String toString() {
    return "DocsDeleteRequest{" +
        "keys=" + keys +
        "} " + super.toString();
  }
}
