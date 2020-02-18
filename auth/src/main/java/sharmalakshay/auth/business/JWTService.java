package sharmalakshay.auth.business;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.stereotype.Service;

import sharmalakshay.auth.utility.AES;

// TODO: Create a refresh token strategy where refresh token is based on actual token

/**
 * JWT Service that provides utility functions for JWT functions
 */
@Service
public class JWTService {

  private String secret = "t[^\"i/Ln~GC%e-9Axj6Ry-@RAAR]/~[$e^t}4>'%";

  /**
   * Generates JWT
   *
   * @return String jwt token
   * @throws Exception
   */
  public String generateToken(Long id) throws JWTCreationException, Exception {
    try {
      Algorithm algorithm = Algorithm.HMAC256(this.secret);
      String token = JWT.create().withIssuer(AES.encrypt("auth.sharmalakshay.com", this.secret)).withSubject("auth")
          .withExpiresAt(new Date(System.currentTimeMillis() + 1800000)).withClaim("uid", id)
          .withClaim("rft", "refreshToken").sign(algorithm);

      return token;
    } catch (JWTCreationException exception) {
      throw exception;
    } catch (Exception exception) {
      throw exception;
    }
  }

  /**
   * Refreshes token if token is invalid based on valid refresh token
   *
   * @param token
   * @param refreshToken
   * @return String jwt token
   * @throws Exception if token verification fails
   */
  public String refreshToken(String token) throws Exception {
    try {

      DecodedJWT decodedJWT = JWT.decode(token);

      if (!AES.decrypt(decodedJWT.getIssuer(), this.secret).equals("auth.sharmalakshay.com")) {
        throw new Exception();
      }

      return generateToken(decodedJWT.getClaim("uid").asLong());

    } catch (JWTVerificationException exception) {
      throw exception;
    } catch (JWTCreationException exception) {
      throw exception;
    }
  }
}