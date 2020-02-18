package sharmalakshay.auth.controller;

import org.springframework.web.bind.annotation.RestController;

import sharmalakshay.auth.business.JWTService;
import sharmalakshay.auth.business.UserService;
import sharmalakshay.auth.exception.UserAlreadyExists;
import sharmalakshay.auth.model.UserCredentials;
import sharmalakshay.auth.model.Response;

import com.auth0.jwt.exceptions.JWTCreationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Auth
 */
@RestController
@RequestMapping(value = "/user")
public class User {

  @Autowired
  private UserService userService;

  @Autowired
  private JWTService jwtService;

  /**
   * Registers user in the db
   *
   * @param userCredentials
   * @return 200 Ok with a token and refresh token
   * @exception UserAlreadyExists    - 409 Conflict
   * @exception JWTCreationException - 500
   * @exception Exception            - 500
   */
  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public ResponseEntity<Response<String>> registerUser(@RequestBody UserCredentials userCredentials) {
    try {
      sharmalakshay.auth.entity.User newUser = this.userService.registerUser(userCredentials);

      return ResponseEntity.ok()
          .body(new Response<String>(this.jwtService.generateToken(newUser.getId()), null, false));
    } catch (UserAlreadyExists exception) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
          .body(new Response<String>(null,
              "User with email: " + userCredentials.getEmail() + " is already registered with auth.sharmalakshay.com",
              true));
    } catch (JWTCreationException exception) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<String>(null,
          "Oops something went wrong. Please retry later. If issue persists submit a ticket at support.sharmalakshay.com",
          true));
    } catch (Exception exception) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<String>(null,
          "Internal Server Error. Please retry later. If issue persists submit a ticket at support.sharmalakshay.com",
          true));
    }
  }
}