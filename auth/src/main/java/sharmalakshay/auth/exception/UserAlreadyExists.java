package sharmalakshay.auth.exception;

import java.io.ObjectStreamException;

/**
 * Exception definition when user already found
 */
public class UserAlreadyExists extends ObjectStreamException {

  /**
   *
   */
  private static final long serialVersionUID = 3652306865316265093L;

  /**
   * Constructor to create a new UserAlreadyExists with the reason given.
   *
   * @param reason a String describing the reason for the exception.
   */
  public UserAlreadyExists(String reason) {
    super(reason);
  }

  /**
   * Constructor to create a new UserAlreadyExists without a reason.
   */
  public UserAlreadyExists() {
    super();
  }

}