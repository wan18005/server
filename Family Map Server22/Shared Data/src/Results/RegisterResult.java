package Results;

/**
 * A class that returns a response based on the success of the operation provided by the service.
 */
public class RegisterResult {

  private String authtoken;
  private String username;
  private String personID;
  private String message;
  boolean success;

  public RegisterResult() {}

  public RegisterResult(String setError, boolean success) {
    this.setMessage(setError);
    this.setSuccess(success);
  }

  /**
   * The constructor for the register result that will either create a success or failure message.
   *
   * @param setAuthToken the authtoken to be assigned to the user if successful.
   * @param setUsername  the username associated with the user.
   * @param setPersonID  the unique personID associated with the user.
   */
  public RegisterResult(String setAuthToken, String setUsername, String setPersonID) {
    this.setAuthToken(setAuthToken);
    this.setUsername(setUsername);
    this.setPersonID(setPersonID);
    this.setSuccess(true);
  }

  public String getAuthtoken() { return authtoken; }
  public String getUsername() { return username; }
  public String getPersonID() { return personID; }
  public String getMessage() { return message; }
  public boolean getSuccess() { return success; }

  public void setAuthToken(String setAuthToken) { this.authtoken = setAuthToken; }
  public void setUsername(String setUsername) { this.username = setUsername; }
  public void setPersonID(String setPersonID) { this.personID = setPersonID; }
  public void setMessage(String setMessage) { this.message = setMessage; }
  private void setSuccess(boolean setSuccess) { this.success = setSuccess; }
}
