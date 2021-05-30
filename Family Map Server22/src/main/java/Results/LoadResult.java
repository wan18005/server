package Results;

/**
 * turn Result to load services
 */
public class LoadResult {

  private String message;
  private boolean success;

  public String getMessage() { return message; }
  public void setMessage(String message) { this.message = message; }

  public boolean getSuccess() { return success; }
  public void setSuccess(boolean setSuccess) { this.success = setSuccess; }

  public LoadResult(String setMessage, boolean success)
  {
    this.setMessage(setMessage);
    this.setSuccess(success);
  }


}
