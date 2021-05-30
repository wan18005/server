package Results;

/**
 * A class that returns a response based on the success of the operation provided by the service.
 */
public class LoadResult {

  private String message;
  private boolean success;

  /**
   * Generates the response body for the service.
   *
   * @param s determines if the result is success or failure.
   */
  public LoadResult(String setMessage, boolean success) {
    this.setMessage(setMessage);
    this.setSuccess(success);
  }

  public String getMessage() { return message; }
  public boolean getSuccess() { return success; }

  public void setMessage(String message) { this.message = message; }
  public void setSuccess(boolean setSuccess) { this.success = setSuccess; }
}
