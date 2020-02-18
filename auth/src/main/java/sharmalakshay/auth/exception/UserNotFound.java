package sharmalakshay.auth.exception;

import java.io.ObjectStreamException;

/**
 * Exception definition when user already found
 */
public class UserNotFound extends ObjectStreamException {

  /**
   *
   */
  private static final long serialVersionUID = -32302836582134789L;

  /**
   * Constructor to create a new UserNotFound with the reason given.
   *
   * @param reason a String describing the reason for the exception.
   */
  public UserNotFound(String reason) {
    super(reason);
  }

  /**
   * Constructor to create a new UserNotFound without a reason.
   */
  public UserNotFound() {
    super();
  }

}