package org.dabudb.dabu.shared.exceptions;

import org.dabudb.dabu.generated.protobufs.Request;

/**
 * An exception throw when a fatal issue is encountered during startup
 */
public class StartupException extends DatastoreRuntimeException {

  /**
   * Constructs a new runtime exception with the specified detail message and
   * cause.  <p>Note that the detail message associated with
   * {@code cause} is <i>not</i> automatically incorporated in
   * this runtime exception's detail message.
   *
   * @param message the detail message (which is saved for later retrieval
   *                by the {@link #getMessage()} method).
   * @param cause   the cause (which is saved for later retrieval by the
   *                {@link #getCause()} method).  (A <tt>null</tt> value is
   *                permitted, and indicates that the cause is nonexistent or
   *                unknown.)
   * @since 1.4
   */
  public StartupException(String message, Throwable cause) {
    //TODO(lwhite): Do something with the cause
    super(
        Request.ErrorCondition.newBuilder()
            .setDescription(message)
            // TODO(lwhite): Should this be the ErrorType?
            .setErrorType(Request.ErrorType.SEVERE_SERVER_EXCEPTION)
            .build()
    );
  }

  /**
   * Constructs a new runtime exception with the specified detail message.
   * The cause is not initialized, and may subsequently be initialized by a
   * call to {@link #initCause}.
   *
   * @param message the detail message. The detail message is saved for
   *                later retrieval by the {@link #getMessage()} method.
   */
  public StartupException(String message) {
    super(
        Request.ErrorCondition.newBuilder()
            .setDescription(message)
            // TODO(lwhite): Should this be the ErrorType?
            .setErrorType(Request.ErrorType.SEVERE_SERVER_EXCEPTION)
            .build()
    );
  }
}
