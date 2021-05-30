package Results;

/**
 * turn Result to register services
 */
public class RegisterResult
{

  private String authtoken;
  private String username;
  private String personID;
  private String message;
  boolean success;

  public String getAuthtoken() { return authtoken; }
  public void setAuthToken(String setAuthToken) { this.authtoken = setAuthToken; }

  public String getUsername() { return username; }
  public void setUsername(String setUsername) { this.username = setUsername; }

  public String getPersonID() { return personID; }
  public void setPersonID(String setPersonID) { this.personID = setPersonID; }

  public String getMessage() { return message; }
  public void setMessage(String setMessage) { this.message = setMessage; }

  public boolean getSuccess() { return success; }
  private void setSuccess(boolean setSuccess) { this.success = setSuccess; }

  public RegisterResult(String Error, boolean success)
  {
    this.setMessage(Error);
    this.setSuccess(success);
  }


  public RegisterResult(String AuthToken, String Username, String PersonID)
  {
    this.setAuthToken(AuthToken);
    this.setUsername(Username);
    this.setPersonID(PersonID);
    this.setSuccess(true);
  }


}
