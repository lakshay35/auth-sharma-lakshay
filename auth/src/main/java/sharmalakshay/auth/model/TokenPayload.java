package sharmalakshay.auth.model;

/**
 * TokenPayload
 */
public class TokenPayload {

  private String token;

  public TokenPayload(String token) {
    this.token = token;
  }

  public TokenPayload() {
  }

  public String getToken() {
    return this.token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}