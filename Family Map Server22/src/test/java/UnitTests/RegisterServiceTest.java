package UnitTests;

import DataAccessObjects.DataAccessException;
import DataAccessObjects.DAO;

import Requests.RegisterRequest;
import Results.RegisterResult;
import Service.Services.ClearService;
import Service.Services.RegisterService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceTest {
  DAO db = new DAO();
  ClearService clearService = new ClearService();


  @BeforeEach
  public void setUp() throws DataAccessException {
    clearService.ClearDatabase();
    Connection conn = db.getConnection();
  }

  @AfterEach
  public void tearDown() throws DataAccessException {
    db.closeConnection(false);
  }

  @Test
  public void RegisterPass() throws DataAccessException {
    RegisterService registerService = new RegisterService();
    RegisterRequest registerRequest = new RegisterRequest("username", "password", "email", "firstname",
            "lastname", "M");

    RegisterResult registerResult = registerService.Register(registerRequest);

    assertNotNull(registerResult.getPersonID());
    assertNotNull(registerResult.getAuthtoken());
    assertNotNull(registerResult.getUsername());
    assertNull(registerResult.getMessage());
    clearService.ClearDatabase();
  }

  @Test
  public void RegisterFail() throws DataAccessException {
    RegisterService registerService = new RegisterService();
    RegisterRequest registerRequest = new RegisterRequest("A", "B", "C", "D",
            "E", "F");
    registerRequest.setGender(null);

    RegisterResult registerResult = registerService.Register(registerRequest);

    assertNotNull(registerResult.getMessage());
    assertEquals("Error: Invalid input.", registerResult.getMessage());
    assertNull(registerResult.getAuthtoken());
    assertNull(registerResult.getUsername());
    assertNull(registerResult.getPersonID());
  }

  @Test
  public void ExistingUserPass() throws DataAccessException {

    RegisterService registerService = new RegisterService();
    RegisterRequest registerRequest = new RegisterRequest("A", "B", "C", "D",
            "E", "F");
    RegisterResult registerResult = registerService.Register(registerRequest);

    registerResult = registerService.Register(registerRequest);

    assertNotNull(registerResult.getMessage());
    assertEquals("Error: Username is already taken by another user.", registerResult.getMessage());
    assertNull(registerResult.getAuthtoken());
    assertNull(registerResult.getUsername());
    assertNull(registerResult.getPersonID());

    clearService.ClearDatabase();
  }

  @Test
  public void DoubleRegisterPass() throws DataAccessException {
    RegisterService registerService = new RegisterService();
    RegisterRequest registerRequest1 = new RegisterRequest("User1", "Pass1", "e1", "User1First",
            "User1Last", "M");
    RegisterRequest registerRequest2 = new RegisterRequest("User2", "Pass2", "e2", "User2First",
            "User2Last", "M");

    RegisterResult registerResult1 = registerService.Register(registerRequest1);
    RegisterResult registerResult2 = registerService.Register(registerRequest2);

    assertNotNull(registerResult1.getPersonID());
    assertNotNull(registerResult1.getAuthtoken());
    assertNotNull(registerResult1.getUsername());
    assertNull(registerResult1.getMessage());


    assertNotNull(registerResult2.getPersonID());
    assertNotNull(registerResult2.getAuthtoken());
    assertNotNull(registerResult2.getUsername());
    assertNull(registerResult2.getMessage());


    clearService.ClearDatabase();
  }


}