package sharmalakshay.auth.model;

/**
 * UserCredentials
 */
public class UserCredentials {

  private String email;
  private String password;

  public UserCredentials(String email, String password) {
    this.email = email;
    this.password = password;
  }

  public String getPassword() {
    return this.password;
  }

  public String getEmail() {
    return this.email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setemail(String email) {
    this.email = email;
  }

}