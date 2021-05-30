package UnitTests;

import DataAccessObjects.DataAccessException;
import Results.ClearResult;
import Service.Services.ClearService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClearServiceTest {

  @Test
  public void ClearPass() throws DataAccessException {
    ClearService clearService = new ClearService();
    ClearResult clearResult = clearService.ClearDatabase();

    assertEquals(clearResult.getMessage(), "Clear succeeded.");
  }
}
