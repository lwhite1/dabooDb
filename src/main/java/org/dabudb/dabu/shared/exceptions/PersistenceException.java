package org.dabudb.dabu.shared.exceptions;

import org.dabudb.dabu.generated.protobufs.Request;

/**
 * Thrown when an exception occurs during persistence that is not covered by any more specific exception case.
 */
public class PersistenceException extends DatastoreRuntimeException {


  public PersistenceException(Request.ErrorCondition condition) {
    super(condition);
  }
}
