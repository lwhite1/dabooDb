package org.dabudb.dabu.shared.msg.reply;

import javax.annotation.concurrent.Immutable;
import java.util.Objects;

/**
 *  A representation of some problem that occurred on the server, that the client should be aware of
 */
@Immutable
public final class ErrorCondition {

  private final ErrorType type;
  private final String description;

  public ErrorCondition(ErrorType type, String description) {
    this.type = type;
    this.description = description;
  }

  public ErrorType getType() {
    return type;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ErrorCondition that = (ErrorCondition) o;
    return getType() == that.getType() &&
        Objects.equals(getDescription(), that.getDescription());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getType(), getDescription());
  }

  @Override
  public String toString() {
    return "ErrorCondition{" +
        "type=" + type +
        ", description='" + description + '\'' +
        '}';
  }

  public enum ErrorType {

    OPTIMISTIC_LOCK_EXCEPTION,
    IO_EXCEPTION,
    FATAL_SERVER_EXCEPTION
  }
}
