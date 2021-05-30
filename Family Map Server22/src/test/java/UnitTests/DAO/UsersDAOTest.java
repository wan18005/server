package UnitTests.DAO;

import DataAccessObjects.*;

import Models.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class UsersDAOTest {
  private DAO dao;
  private User TestUser;
  private UsersDAO uDao;

  @BeforeEach
  public void setUp() throws DataAccessException {

    dao = new DAO();
    TestUser = new User("7758", "123", "abc@gmail.com",
            "Yi", "Wa", "M", "YiWA001");


    //open connection
    Connection connection = dao.getConnection();
    // clear the table
    dao.ClearTables();
    // pass to UDAO
    uDao = new UsersDAO(connection);
  }

  @AfterEach
  public void shutDown() throws DataAccessException {
   //close it after each test is done
    dao.closeConnection(false);
  }

  @Test
  void InsertPass() throws DataAccessException {
    uDao.Insert(TestUser);

    User compareTest = uDao.Find(TestUser.getUsername());

    assertNotNull(compareTest);

    assertEquals(TestUser, compareTest);
  }

  @Test
  void InsertFail() throws DataAccessException {
    uDao.Insert(TestUser);

    assertThrows(DataAccessException.class, () -> uDao.Insert(TestUser));
  }

/*
    TestUser = new User("7758", "123", "abc@gmail.com",
            "Yi", "Wa", "M", "YiWA001");
 */
  @Test
  void FindPass() {
    try {
      assertNull(uDao.Find("7758"));
      uDao.Insert(TestUser);
      assertNotNull(uDao.Find("7758"));
      //find 7758 in test user
      User testUser = uDao.Find("7758");
      assertEquals(TestUser, testUser);
    } catch (DataAccessException e) {
      System.out.println("Error encountered while finding User\n");
    }
  }

  @Test
  void FindFail() {
    try {
      // find 7758 then insert
      assertNull(uDao.Find("7758"));
      uDao.Insert(TestUser);
      //find 7758
      assertNotNull(uDao.Find("7758"));
      // find 1234
      assertNull(uDao.Find("1234"));
      //not match fail
    } catch (DataAccessException e) {
      System.out.println("Error encountered while finding User\n");
    }
  }

  @Test
  void Clear() {
    try {
      assertNull(uDao.Find("7758"));
      uDao.Insert(TestUser);
      //find user 7758
      assertNotNull(uDao.Find("7758"));
      // if find clear
      assertTrue(uDao.Clear());

    } catch (DataAccessException e) {
      System.out.println("Error encountered while finding User\n");
    }
  }
}
