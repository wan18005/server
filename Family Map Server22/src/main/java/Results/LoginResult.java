package Results;

/**
 * A class that returns a response based on the success of the operation provided by the service.
 */
public class LoginResult {

  private String authtoken;
  private String username;
  private String personID;
  private String message;
  private boolean success;

  public LoginResult(String setMessage, boolean setSuccess) {
    this.setMessage(setMessage);
    this.setSuccess(setSuccess);
  }

  public LoginResult(String setAuthToken, String setUsername, String setPersonID) {
    this.setAuthtoken(setAuthToken);
    this.setUsername(setUsername);
    this.setPersonID(setPersonID);
    this.setSuccess(true);
  }

  public String getAuthtoken() { return authtoken; }
  public String getUsername() { return username; }
  public String getPersonID() { return personID; }
  public String getMessage() { return message; }
  public boolean getSuccess() { return success; }

  public void setAuthtoken(String setAuthToken) { this.authtoken = setAuthToken; }
  public void setUsername(String setUsername) { this.username = setUsername; }
  public void setPersonID(String setPersonID) { this.personID = setPersonID; }
  public void setMessage(String setMessage) { this.message = setMessage; }
  private void setSuccess(boolean setSuccess) { this.success = setSuccess; }
}
