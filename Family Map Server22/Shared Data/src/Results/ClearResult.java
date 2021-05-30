package Results;

/**
 * A class that returns a response based on the success of the operation provided by the service.
 */
public class ClearResult {

  private String message;
  private boolean success;

  public ClearResult(String setMessage, boolean setSuccess) {
    this.setMessage(setMessage);
    this.setSuccess(setSuccess);
  }

  public String getMessage() { return message; }
  public boolean getSuccess() { return success; }

  public void setMessage(String message) { this.message = message; }
  public void setSuccess(boolean setSuccess) {this.success = setSuccess; }
}
