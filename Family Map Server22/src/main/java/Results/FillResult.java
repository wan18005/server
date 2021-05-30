package Results;

/**
 * turn Result to fill services
 */
public class FillResult {
  // var
  private String message;
  private boolean success;

  //getter //setter
  public String getMessage() { return message; }
  public void setMessage(String setMessage) { this.message = setMessage; }

  public boolean getSuccess() { return success; }
  public void setSuccess(boolean setSuccess) { this.success = setSuccess; }
  // constructor
  public FillResult(String setMessage, boolean setSuccess)
  {
    this.setMessage(setMessage);
    this.setSuccess(setSuccess);
  }

}
