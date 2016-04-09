package com.deathrayresearch.dabu.shared.msg;

import javax.annotation.concurrent.Immutable;

/**
 * A request to execute a query that returns zero or more objects
 *
 * TODO(lwhite): see if this can be folded into a ReadRequest with GetRequest()
 */
@Immutable
public final class QueryRequest extends AbstractRequest {

  QueryRequest(RequestType requestType) {
    super(requestType);
  }
}
