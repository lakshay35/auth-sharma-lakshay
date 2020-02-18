package sharmalakshay.auth.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sharmalakshay.auth.entity.User;
import sharmalakshay.auth.exception.UserAlreadyExists;
import sharmalakshay.auth.exception.UserNotFound;
import sharmalakshay.auth.model.UserCredentials;
import sharmalakshay.auth.repository.UserRepository;
import sharmalakshay.auth.utility.AES;

/**
 * UserService
 */
@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  private String secret = "t[^\"i/Ln~GC%e-9Axj6Ry-@RAAR]/~[$e^t}4>'%";

  /**
   * Authenticates user based on provided credentials
   *
   * @param userCredentials
   * @return Boolean indicating authentication status
   */
  public User authenticateUser(UserCredentials userCredentials) throws UserNotFound, Exception {
    try {
      User user = this.userRepository.findByEmail(userCredentials.getEmail());

      if (user == null) {
        throw new UserNotFound();
      }

      if (!AES.decrypt(user.getPassword(), this.secret).equals(userCredentials.getPassword())) {
        throw new Exception();
      }

      return user;
    } catch (UserNotFound exception) {
      throw exception;
    } catch (Exception exception) {
      throw new Exception("Error decrypting user password");
    }
  }

  /**
   * Registers user based on credentials
   *
   * @param userCredentials
   * @return Boolean indicating registration status
   */
  public User registerUser(UserCredentials userCredentials) throws UserAlreadyExists, Exception {
    try {
      if (this.userRepository.existsByEmail(userCredentials.getEmail())) {
        throw new UserAlreadyExists();
      }
      return this.userRepository
          .save(new User(userCredentials.getEmail(), AES.encrypt(userCredentials.getPassword(), this.secret)));
    } catch (UserAlreadyExists exception) {
      throw exception;
    } catch (Exception exception) {
      throw new Exception("Error encrypting user password");
    }
  }
}