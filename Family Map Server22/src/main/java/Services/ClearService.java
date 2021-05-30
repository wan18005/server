package Services;

import DataAccessObjects.DataAccessException;
import DataAccessObjects.DAO;
import Results.*;

/**
 * Class that will handle Clear business logic and determine if a request was successful.
 */
public class ClearService {

  private ClearResult clearResult;

  /**
   * Function that clears the database.
   *
   * @return a result to determine if the database was cleared or not.
   */
  public ClearResult ClearDatabase() throws DataAccessException
  {
    DAO dao = new DAO();
    try {
      dao.getConnection();
      dao.ClearTables();
      dao.closeConnection(true);
      clearResult = new ClearResult("clear succeeded.", true);
      return clearResult;
    }
    catch (DataAccessException e)
    {
      dao.closeConnection(false);
      clearResult = new ClearResult("Error: The database was not cleared successfully.", false);
      return clearResult;
    }
  }
}
