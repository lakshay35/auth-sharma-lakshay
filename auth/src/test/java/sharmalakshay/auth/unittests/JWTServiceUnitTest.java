package sharmalakshay.auth.unittests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import sharmalakshay.auth.business.JWTService;

/**
 * JWTServiceUnitTest
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = JWTService.class)
public class JWTServiceUnitTest {

  @Autowired
  private JWTService service;

  @Test
  public void createToken_shouldReturnJwt() {
    try {
      String result = this.service.generateToken((long) 1);
      assertTrue(Pattern.matches("[a-zA-Z0-9-_]+.[a-zA-Z0-9-_=]+.[a-zA-Z0-9-_=]+", result));
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  @Test
  public void refreshToken_shouldReturnJwt() {
    try {
      String result = this.service.generateToken((long) 1);
      result = this.service.refreshToken(result);
      assertTrue(Pattern.matches("[a-zA-Z0-9-_]+.[a-zA-Z0-9-_]+.[a-zA-Z0-9-_]+", result));
    } catch (Exception e) {
      fail();
    }
  }

}