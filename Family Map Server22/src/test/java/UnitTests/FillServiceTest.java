package UnitTests;

import Models.AuthToken;
import Models.User;

import Requests.RegisterRequest;
import Results.FillResult;
import Results.PersonResult;
import Results.RegisterResult;
import Service.Services.ClearService;
import Service.Services.FillService;
import Service.Services.PersonService;
import Service.Services.RegisterService;

import DataAccessObjects.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class FillServiceTest {
  DAO db = new DAO();
  ClearService clearService = new ClearService();

  RegisterRequest registerRequest;
  Connection conn;


  @BeforeEach
  public void setUp() throws SQLException, DataAccessException {
    ClearService clearService = new ClearService();
    clearService.ClearDatabase();
    conn = db.getConnection();

    AuthTokenDAO authTokenDAO = new AuthTokenDAO(conn);

    registerRequest = new RegisterRequest("OptimusPrime", "autobots", "stars@yahoo.com",
            "Optimus", "Prime", "R");

    authTokenDAO.Insert(new AuthToken("OptimusPrime", "1111"));
    authTokenDAO.Insert(new AuthToken("Megatron", "2222"));

    conn.commit();
  }

  @AfterEach
  public void tearDown() throws DataAccessException {
    clearService.ClearDatabase();
    db.closeConnection(false);
  }

  @Test
  public void FillPass() throws DataAccessException {
    PersonService personService = new PersonService();
    PersonResult personResult = personService.Person("1111");

    RegisterService registerService = new RegisterService();
    RegisterResult registerResult = registerService.Register(registerRequest);

    assertNull(personResult.getData());
    assertEquals("Error: OptimusPrime has no associated Persons.", personResult.getMessage());

    FillService fillService = new FillService();
    FillResult fillResult = fillService.Fill("OptimusPrime", 4);

    personResult = personService.Person("1111");
    assertNotNull(personResult.getData());
    assertEquals(personResult.getData().size(), 31);
    assertNotEquals("User was not found.", fillResult.getMessage());
    assertNotEquals("Number of generations is not valid.", fillResult.getMessage());
    assertNotEquals("FatalError", fillResult.getMessage());
  }

  @Test
  public void FillFail() throws DataAccessException {
    PersonService personService = new PersonService();
    PersonResult personResult = personService.Person("2222");
    assertNull(personResult.getData());
    assertEquals("Error: Megatron has no associated Persons.", personResult.getMessage());

    FillService fillService = new FillService();
    FillResult fillResult = fillService.Fill("Megatron", - 1);

    assertEquals("Error: Number of generations is less than or equal to 0.", fillResult.getMessage());
  }
}
