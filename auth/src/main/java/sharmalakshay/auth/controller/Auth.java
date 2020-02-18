package sharmalakshay.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import sharmalakshay.auth.business.JWTService;
import sharmalakshay.auth.business.UserService;
import sharmalakshay.auth.exception.UserNotFound;
import sharmalakshay.auth.model.Response;
import sharmalakshay.auth.model.TokenPayload;
import sharmalakshay.auth.model.UserCredentials;

/**
 * Auth Controller
 */
@RestController
@RequestMapping(value = "/auth")
public class Auth {

  @Autowired
  private JWTService jwtService;

  @Autowired
  private UserService userService;

  /**
   * Authenticates user based on provided credentials
   *
   * @param userCredentials
   * @return ResponseEntity<String> with token or a bad request
   */
  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
  public ResponseEntity<Response<String>> authenticateUser(@RequestBody UserCredentials userCredentials) {
    try {
      sharmalakshay.auth.entity.User user = this.userService.authenticateUser(userCredentials);
      String token = this.jwtService.generateToken(user.getId());

      return ResponseEntity.ok().body(new Response<String>(token, null, false));
    } catch (UserNotFound exception) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response<String>("", "User not found", true));
    } catch (Exception exception) {
      return ResponseEntity.badRequest().body(new Response<String>("", "Unable to create token", true));
    }
  }

  /**
   * Refreshes token
   *
   * @param refreshPayload
   * @return 200 Ok New refreshed token and refresh token
   * @exception Exception - 400
   */
  @RequestMapping(value = "/refresh", method = RequestMethod.POST)
  public ResponseEntity<Response<String>> refreshToken(@RequestBody TokenPayload tokenPayload) {
    try {
      String token = this.jwtService.refreshToken(tokenPayload.getToken());

      return ResponseEntity.ok().body(new Response<String>(token, null, false));
    } catch (Exception exception) {
      return ResponseEntity.badRequest().body(new Response<String>(null, "Unable to refresh token", false));
    }
  }

}