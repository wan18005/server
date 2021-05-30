package Results;

/**
 * turn Result to login services
 */
public class LoginResult {
//var
  private String authtoken;
  private String username;
  private String personID;
  private String message;
  private boolean success;
// getter setter
  public void setUsername(String setUsername) { this.username = setUsername; }
  public String getUsername() { return username; }

  public void setPersonID(String setPersonID) { this.personID = setPersonID; }
  public String getPersonID() { return personID; }

  public String getAuthtoken() { return authtoken; }
  public void setAuthtoken(String setAuthToken) { this.authtoken = setAuthToken; }

  public String getMessage() { return message; }
  public void setMessage(String setMessage) { this.message = setMessage; }

  public boolean getSuccess() { return success; }
  private void setSuccess(boolean setSuccess) { this.success = setSuccess; }
//constructor
  public LoginResult(String setMessage, boolean setSuccess)
  {
    this.setMessage(setMessage);
    this.setSuccess(setSuccess);
  }

  public LoginResult(String setAuthToken, String setUsername, String setPersonID)
  {
    this.setAuthtoken(setAuthToken);
    this.setUsername(setUsername);
    this.setPersonID(setPersonID);
    this.setSuccess(true);
  }


}
