package Results;

/**
 * turn Clear Result to events services
 */
public class ClearResult
{
  // variable
  private String messageOutput;
  private boolean success;

  //getter
  public String getMessageOutput() {return messageOutput;}
  public void setMessageOutput(String messageOutput) {this.messageOutput=messageOutput;}
  //setter
  public boolean getSuccess() { return success; }
  public void setSuccess(boolean setSuccess) {this.success = setSuccess; }


  // constructor
  public ClearResult(String messageOutput, boolean setSuccess)
  {
    this.setMessageOutput(messageOutput);
    this.setSuccess(setSuccess);
  }

}
