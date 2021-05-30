package Models;

import java.util.UUID;

/**
 * Authorization Token class that create an authentic token for the user and assigns this token to that
 * specific users userName.
 */
public class AuthToken {

  private String authtoken;
  private String username;

  /**
   * The default constructor that initializes all of the local variables to null.
   */
  public AuthToken() {
    authtoken = UUID.randomUUID().toString();
    username = null;
  }

  /**
   * Parameterized constructor that accepts an already generated username and randomly creates an authToken
   * for the user
   *
   * @param setUsername the already generated userName
   */
  public AuthToken(String setUsername) {
    this.username = setUsername;
    this.authtoken = UUID.randomUUID().toString();
  }

  /**
   * Parameterized constructor that accepts an already generated authToken and a username
   *
   * @param setUsername  the already generated username for this authToken
   * @param setAuthToken the already generated authToken as this authToken
   */
  public AuthToken(String setUsername, String setAuthToken) {
    this.username = setUsername;
    this.authtoken = setAuthToken;
  }

  public String getAuthtoken() { return authtoken; }
  public String getUsername() { return username; }

  public void setAuthtoken(String authtoken) { this.authtoken = authtoken; }
  public void setUsername(String username) { this.username = username; }
}
