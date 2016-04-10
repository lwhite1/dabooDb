package org.dabudb.dabu.shared.msg.reply;

import org.dabudb.dabu.shared.msg.request.Request;

import javax.annotation.concurrent.Immutable;
import java.util.Arrays;
import java.util.Objects;

/**
 * A reply to a get request for a single document
 */
@Immutable
public final class DocGetReply extends AbstractReply {

  private final byte[] document;

  public DocGetReply(Request request, byte[] document) {
    super(request);
    this.document = document;
  }

  public byte[] getDocument() {
    return document;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    DocGetReply that = (DocGetReply) o;
    return Arrays.equals(getDocument(), that.getDocument());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getDocument());
  }

  @Override
  public String toString() {
    return "DocGetReply{" +
        "document=" + Arrays.toString(document) +
        "} " + super.toString();
  }
}
