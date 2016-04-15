package org.dabudb.dabu.shared.exceptions;

import org.dabudb.dabu.shared.protobufs.Request;

/**
 *  General exception for an exception occurring during database operation
 */
public class DatastoreException extends Exception {

  private final Request.ErrorCondition errorCondition;

  public DatastoreException(Request.ErrorCondition condition) {
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
