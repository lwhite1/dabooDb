package org.dabudb.dabu.shared.msg.request;

import java.util.List;
import java.util.Objects;

/**
 *  A request to get a collection of documents using their document keys
 */
public class DocsGetRequest extends AbstractRequest {

  private final List<byte[]> keys;


  public DocsGetRequest(List<byte[]> keys) {
    super(RequestType.GET);
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
    DocsGetRequest that = (DocsGetRequest) o;
    return Objects.equals(getKeys(), that.getKeys());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getKeys());
  }

  @Override
  public String toString() {
    return "DocsGetRequest{" +
        "keys=" + keys +
        "} " + super.toString();
  }
}
