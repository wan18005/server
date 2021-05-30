package UnitTests;

import DataAccessObjects.DataAccessException;
import DataAccessObjects.DAO;
import DataAccessObjects.UsersDAO;

import Models.User;

import Requests.LoginRequest;
import Results.LoginResult;
import Service.Services.ClearService;
import Service.Services.LoginService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {
  ClearService clearService = new ClearService();
  LoginService loginService = new LoginService();
  DAO db = new DAO();

  Connection conn;
  UsersDAO UsersDAO;
  LoginResult loginResult;


  @BeforeEach
  public void setUp() throws SQLException, DataAccessException {
    conn = db.getConnection();
    clearService.ClearDatabase();

    UsersDAO = new UsersDAO(conn);

    User uOne = new User("OptimusPrime", "autobotsrox", "OptimusRocks@gmail.com", "Optimus", "Prime", "R", "Optimus123");
    User uTwo = new User("Megatron", "OptimusIsSilly", "MegaRocks@gmail.com", "Mega", "Tron", "R", "MegaMega123321Babylessgo!");

    UsersDAO.Insert(uOne);
    UsersDAO.Insert(uTwo);
    conn.commit();
  }


  @AfterEach
  public void tearDown() throws DataAccessException {
    clearService.ClearDatabase();
    db.closeConnection(false);
  }

  @Test
  public void LoginPass() throws DataAccessException, SQLException {
    loginResult = loginService.login(new LoginRequest("OptimusPrime", "autobotsrox"));

    assertEquals("OptimusPrime", loginResult.getUsername());
    assertNull(loginResult.getMessage());
    assertNotNull(loginResult.getPersonID());
    assertNotNull(loginResult.getAuthtoken());

  }

  @Test
  public void LoginFailBadUser() throws SQLException, DataAccessException {
    UsersDAO.Clear();
    conn.commit();

    loginResult = loginService.login(new LoginRequest("Megatron", "OptimusIsSilly"));
    assertNull(loginResult.getAuthtoken());
    assertNull(loginResult.getUsername());
    assertNull(loginResult.getPersonID());
    assertNotNull(loginResult.getMessage());
    assertEquals("Error: Unable to retrieve requested user, user does not exist.", loginResult.getMessage());
  }

  @Test
  public void LoginFailBadPassword() throws DataAccessException, SQLException {
    loginResult = loginService.login(new LoginRequest("OptimusPrime", "autobotsroxx"));

    assertNull(loginResult.getAuthtoken());
    assertNull(loginResult.getUsername());
    assertNull(loginResult.getPersonID());
    assertNotNull(loginResult.getMessage());
    assertEquals("Error: Password is incorrect.", loginResult.getMessage());
  }
}