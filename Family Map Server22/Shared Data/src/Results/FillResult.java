package Results;

/**
 * A class that returns a response based on the success of the operation provided by the service.
 */
public class FillResult {

  private String message;
  private boolean success;

  public FillResult(String setMessage, boolean setSuccess) {
    this.setMessage(setMessage);
    this.setSuccess(setSuccess);
  }

  public String getMessage() { return message; }
  public boolean getSuccess() { return success; }

  public void setMessage(String setMessage) { this.message = setMessage; }
  public void setSuccess(boolean setSuccess) { this.success = setSuccess; }
}
