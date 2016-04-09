package com.deathrayresearch.dabu.shared.msg;

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
