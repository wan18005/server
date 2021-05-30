package UnitTests.DAO;

import DataAccessObjects.AuthTokenDAO;
import DataAccessObjects.DataAccessException;
import DataAccessObjects.DAO;
import Models.AuthToken;
import Services.ClearService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the AuthTokenDAO class.
 * Tests:
 *  InsertPass
 *  InsertFail
 *  FindPass
 *  FindFail
 */
class AuthTokenDAOTest {
  DAO db = new DAO();
  ClearService clearService = new ClearService();

  Connection conn;
  AuthToken optimusToken;
  AuthToken megatronToken;
  AuthToken bumbleToken;
  AuthTokenDAO aDAO;


  @BeforeEach
  void setUp() throws DataAccessException {
    clearService.ClearDatabase();
    conn = db.getConnection();

    aDAO = new AuthTokenDAO(conn);

    optimusToken = new AuthToken("NoBIBI", "nobibi123");
    megatronToken = new AuthToken("Mark", "qwert");
    bumbleToken = new AuthToken("gg", "9000");

  }

  @AfterEach
  void tearDown() {
    try {
      db.closeConnection(false);
      clearService.ClearDatabase();
    } catch (DataAccessException e) {
      e.printStackTrace();
    }
  }

  /**
   * Inserts 3 AuthTokens into the AuthToken table and verifies that they were inserted correctly.
   * Test passes when each User's username and authtoken match the User that was inserted.
   * @throws DataAccessException when something goes wrong with Find.
   * @throws SQLException when something goes wrong with Insert.
   */
  @Test
  void InsertPass() throws DataAccessException, SQLException {
    assertNull(aDAO.Find("nobibi123"));
    assertNull(aDAO.Find("qwert"));
    assertNull(aDAO.Find("9000"));

    aDAO.Insert(optimusToken);
    aDAO.Insert(megatronToken);
    aDAO.Insert(bumbleToken);

    conn.commit();

    AuthToken tempOptimus = aDAO.Find("nobibi123");
    AuthToken tempMegatron = aDAO.Find("qwert");
    AuthToken tempBumble = aDAO.Find("9000");

    assertNotNull(tempOptimus);
    assertNotNull(tempMegatron);
    assertNotNull(tempBumble);

    assertEquals("NoBIBI", tempOptimus.getUsername());
    assertEquals("nobibi123", tempOptimus.getAuthtoken());
    assertEquals("Mark", tempMegatron.getUsername());
    assertEquals("qwert", tempMegatron.getAuthtoken());
    assertEquals("gg", tempBumble.getUsername());
    assertEquals("9000", tempBumble.getAuthtoken());
  }

  /**
   * Inserts 2 AuthTokens into the AuthToken table and verifies that they were inserted correctly.
   * Insert fails when the same tokens are attempted to be inserted again.
   * After Insert fails the test ensures that the first 2 tokens that were inserted are still
   * correclty stored in the database.
   * @throws DataAccessException when something goes wrong with Find.
   * @throws SQLException when something goes wrong with Insert.
   */
  @Test
  void InsertFail() throws DataAccessException, SQLException {
    assertNull(aDAO.Find("nobibi123"));
    assertNull(aDAO.Find("qwert"));
    assertNull(aDAO.Find("9000"));

    aDAO.Insert(optimusToken);
    aDAO.Insert(megatronToken);
    assertThrows(DataAccessException.class, () -> aDAO.Insert(optimusToken));
    assertThrows(DataAccessException.class, () -> aDAO.Insert(megatronToken));

    conn.commit();

    AuthToken tempOptimus = aDAO.Find("nobibi123");
    AuthToken tempMegatron = aDAO.Find("qwert");

    assertNotNull(tempOptimus);
    assertNotNull(tempMegatron);

    assertEquals("NoBIBI", tempOptimus.getUsername());
    assertEquals("nobibi123", tempOptimus.getAuthtoken());
    assertEquals("Mark", tempMegatron.getUsername());
    assertEquals("qwert", tempMegatron.getAuthtoken());
  }

  /**
   *     optimusToken = new AuthToken("NoBIBI", "nobibi123");
   *     megatronToken = new AuthToken("Mark", "qwert");
   *     bumbleToken = new AuthToken("gg", "9000");
   */
  @Test
  void FindPass() throws DataAccessException, SQLException {
    assertNull(aDAO.Find("nobibi123"));
    assertNull(aDAO.Find("qwert"));
    assertNull(aDAO.Find("9000"));

    aDAO.Insert(optimusToken);
    aDAO.Insert(megatronToken);
    aDAO.Insert(bumbleToken);

    conn.commit();

    AuthToken tempOptimus = aDAO.Find("nobibi123");
    AuthToken tempMegatron = aDAO.Find("qwert");
    AuthToken tempBumble = aDAO.Find("9000");

    assertNotNull(tempOptimus);
    assertNotNull(tempMegatron);
    assertNotNull(tempBumble);
    assertEquals("NoBIBI", tempOptimus.getUsername());
    assertEquals("nobibi123", tempOptimus.getAuthtoken());
    assertEquals("Mark", tempMegatron.getUsername());
    assertEquals("qwert", tempMegatron.getAuthtoken());
    assertEquals("gg", tempBumble.getUsername());
    assertEquals("9000", tempBumble.getAuthtoken());
  }

  /**
   * Inserts 3 AuthTokens into the AuthToken table and then attempts to find those 3 tokens again.
   * Test passes when it verifies that each token it is attempting to find does not exist, and the Find
   * method returns null.
   * @throws DataAccessException when something goes wrong with Find.
   * @throws SQLException when something goes wrong with Insert.
   */
  @Test
  void FindFail() throws DataAccessException, SQLException {
    assertNull(aDAO.Find("nobibi123"));
    assertNull(aDAO.Find("qwert"));
    assertNull(aDAO.Find("9000"));

    aDAO.Insert(optimusToken);
    aDAO.Insert(megatronToken);
    aDAO.Insert(bumbleToken);

    conn.commit();

    AuthToken tempOptimus = aDAO.Find("12");
    AuthToken tempMegatron = aDAO.Find("123");
    AuthToken tempBumble = aDAO.Find("90003");

    assertNull(tempOptimus);
    assertNull(tempMegatron);
    assertNull(tempBumble);
  }
}