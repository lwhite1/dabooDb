package org.dabudb.dabu.shared.msg.reply;

import org.dabudb.dabu.shared.msg.request.Request;

import javax.annotation.concurrent.Immutable;

/**
 * Reply to a document write request
 */
@Immutable
public final class WriteReply extends AbstractReply {

  public WriteReply(Request request) {
    super(request);
  }
}
