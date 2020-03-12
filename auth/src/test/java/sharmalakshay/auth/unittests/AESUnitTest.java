package sharmalakshay.auth.unittests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import sharmalakshay.auth.utility.AES;

/**
 * AESUnitTest
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AES.class)
public class AESUnitTest {

  private String secret = "t[^\"i/Ln~GC%e-9Axj6Ry-@RAAR]/~[$e^t}4>'%";

  @Test
  public void encryptPassword_thenReturnEncryptedPassword() {
    try {
      String result = AES.encrypt("password", secret);

      assertEquals(result, "wZjr5fDldl+h3ENIgluCSA==");
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void decryptPassword_thenReturnDecryptedPassword() {
    try {
      String result = AES.decrypt("wZjr5fDldl+h3ENIgluCSA==", secret);

      assertEquals(result, "password");
    } catch (Exception e) {
      fail();
    }
  }
}