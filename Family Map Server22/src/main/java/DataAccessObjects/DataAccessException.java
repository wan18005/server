package DataAccessObjects;

public class DataAccessException extends Exception
{
  public DataAccessException(String ErrorMessage)
  {
    // reference to super class parents
    super(ErrorMessage);
  }

  DataAccessException()
  {
    super();
  }
}