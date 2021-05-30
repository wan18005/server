package UnitTests;

import DataAccessObjects.DataAccessException;
import Results.ClearResult;
import Services.ClearService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ClearServiceTest
{

  @Test
  public void ClearPass() throws DataAccessException
  {
    ClearService clearService = new ClearService();
    ClearResult clearResult = clearService.ClearDatabase();
    // compare the two message output if it is
    assertEquals(clearResult.getMessageOutput(), "clear succeeded.");
  }

  @Test
  public void ClearFail() throws DataAccessException
  {
    ClearService clearService = new ClearService();
    ClearResult clearResult = clearService.ClearDatabase();
    // compare the two message output if it is
    assertNotNull("123");

  }
}
