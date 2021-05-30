package UnitTests;

import DataAccessObjects.*;

import Models.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class UsersDAOTest {
  private DAO db;
  private User bestUser;
  private UsersDAO uDao;

  @BeforeEach
  public void setUp() throws DataAccessException {
    //here we can set up any classes or variables we will need for the rest of our tests
    //lets create a new database
    db = new DAO();
    //and a new user with random data
    bestUser = new User("OptimusPrime123", "MegatronIsDumb", "OptimusRocks@gmail.com",
            "Optimus", "Prime", "M", "Optimus123");


    //Here, we'll open the connection in preparation for the test case to use it
    Connection conn = db.getConnection();
    //Let's clear the database as well so any lingering data doesn't affect our tests
    db.ClearTables();
    //Then we pass that connection to the UserDAO so it can access the database
    uDao = new UsersDAO(conn);
  }

  @AfterEach
  public void tearDown() throws DataAccessException {
    //Here we close the connection to the database file so it can be opened elsewhere.
    //We will leave commit to false because we have no need to save the changes to the database
    //between test cases
    db.closeConnection(false);
  }

  @Test
  void InsertPass() throws DataAccessException {
    uDao.Insert(bestUser);

    User compareTest = uDao.Find(bestUser.getUsername());

    assertNotNull(compareTest);

    assertEquals(bestUser, compareTest);
  }

  @Test
  void InsertFail() throws DataAccessException {
    uDao.Insert(bestUser);

    assertThrows(DataAccessException.class, () -> uDao.Insert(bestUser));
  }


  @Test
  void FindPass() {
    try {
      assertNull(uDao.Find("OptimusPrime123"));
      uDao.Insert(bestUser);
      assertNotNull(uDao.Find("OptimusPrime123"));

      User testUser = uDao.Find("OptimusPrime123");
      assertEquals(bestUser, testUser);
    } catch (DataAccessException e) {
      System.out.println("Error encountered while finding User\n");
    }
  }

  @Test
  void FindFail() {
    try {
      assertNull(uDao.Find("OptimusPrime123"));
      uDao.Insert(bestUser);
      assertNotNull(uDao.Find("OptimusPrime123"));

      assertNull(uDao.Find("Doesn'tExist"));
    } catch (DataAccessException e) {
      System.out.println("Error encountered while finding User\n");
    }
  }

  @Test
  void Clear() {
    try {
      assertNull(uDao.Find("OptimusPrime123"));
      uDao.Insert(bestUser);
      assertNotNull(uDao.Find("OptimusPrime123"));

      assertTrue(uDao.Clear());
    } catch (DataAccessException e) {
      System.out.println("Error encountered while finding User\n");
    }
  }
}
