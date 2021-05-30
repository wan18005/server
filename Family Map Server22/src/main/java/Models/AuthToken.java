package Models;

import java.util.UUID;

/**
 * Authorization Token Model class
 */
public class AuthToken {

  private String authtoken;
  private String username;

//default
  public AuthToken() {
    authtoken = UUID.randomUUID().toString();
    username = null;
  }
  // random auth token but with a set username
  public AuthToken(String setUsername) {
    this.username = setUsername;
    this.authtoken = UUID.randomUUID().toString();
  }
  //constructor with username and authtoken
  public AuthToken(String setUsername, String setAuthToken) {
    this.username = setUsername;
    this.authtoken = setAuthToken;
  }
  // getter by done by code generate
  public String getAuthtoken() { return authtoken; }
  public String getUsername() { return username; }
  // setter by done by code generate
  public void setAuthtoken(String authtoken) { this.authtoken = authtoken; }
  public void setUsername(String username) { this.username = username; }
}
