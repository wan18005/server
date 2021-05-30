package Requests;

/**
 * A class that packages a gson request and turns it into a Java object.
 */
public class LoginRequest {

  private String username;
  private String password;

  public LoginRequest() {
    username = null;
    password = null;
  }

  public LoginRequest(String setUsername, String setPassword) {
    this.setUsername(setUsername);
    this.setPassword(setPassword);
  }

  public String getUsername() {
    return username;
  }
  public String getPassword() { return password; }

  public void setUsername(String setUsername) {
    this.username = setUsername;
  }
  public void setPassword(String setPassword) {
    this.password = setPassword;
  }
}
