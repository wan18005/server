package UnitTests;

import DataAccessObjects.DataAccessException;
import DataAccessObjects.DAO;
import DataAccessObjects.UsersDAO;

import Models.User;

import Requests.LoginRequest;
import Results.LoginResult;
import Services.ClearService;
import Services.LoginService;

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

    User TestOne = new User("aa", "aa", "aa@gmail.com", "a", "a", "M", "a1");
    User TestTwo = new User("bb", "bb", "bb@gmail.com", "b", "b", "F", "b1!");

    UsersDAO.Insert(TestOne);
    UsersDAO.Insert(TestTwo);
    conn.commit();
  }


  @AfterEach
  public void tearDown() throws DataAccessException {
    clearService.ClearDatabase();
    db.closeConnection(false);
  }

  @Test
  public void LoginPass() throws DataAccessException, SQLException {
    loginResult = loginService.login(new LoginRequest("aa", "aa"));

    assertEquals("aa", loginResult.getUsername());
    assertNull(loginResult.getMessage());
    assertNotNull(loginResult.getPersonID());
    assertNotNull(loginResult.getAuthtoken());

  }

  @Test
  public void LoginFailBadUser() throws SQLException, DataAccessException {
    UsersDAO.Clear();
    conn.commit();

    loginResult = loginService.login(new LoginRequest("bb", "bb"));
    assertNull(loginResult.getAuthtoken());
    assertNull(loginResult.getUsername());
    assertNull(loginResult.getPersonID());
    assertNotNull(loginResult.getMessage());
    assertEquals("Error: user does not exist.", loginResult.getMessage());
  }

  @Test
  public void LoginFailBadPassword() throws DataAccessException, SQLException {
    loginResult = loginService.login(new LoginRequest("aa", "llll"));

    assertNull(loginResult.getAuthtoken());
    assertNull(loginResult.getUsername());
    assertNull(loginResult.getPersonID());
    assertNotNull(loginResult.getMessage());
    assertEquals("Error: Password does not match.", loginResult.getMessage());
  }
}