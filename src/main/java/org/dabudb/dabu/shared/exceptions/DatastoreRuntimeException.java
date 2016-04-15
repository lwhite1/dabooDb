package org.dabudb.dabu.shared.exceptions;

import org.dabudb.dabu.generated.protobufs.Request;

/**
 *  General unchecked exception for a system or JVM exception occurring during a database operation
 */
public class DatastoreRuntimeException extends RuntimeException {

  private final Request.ErrorCondition errorCondition;

  public DatastoreRuntimeException(Request.ErrorCondition condition) {
    super();
    this.errorCondition = condition;
  }

  /**
   * Returns the error condition that caused this event to be thrown
   */
  public Request.ErrorCondition getErrorCondition() {
    return errorCondition;
  }
}
