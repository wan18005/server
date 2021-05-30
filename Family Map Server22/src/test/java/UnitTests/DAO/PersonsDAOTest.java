package UnitTests.DAO;

import DataAccessObjects.DataAccessException;
import DataAccessObjects.DAO;
import DataAccessObjects.PersonsDAO;
import DataAccessObjects.UsersDAO;

import Models.Person;
import Models.User;

import Services.ClearService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class PersonsDAOTest {
  private DAO db = new DAO();
  ClearService clearService = new ClearService();

  Connection conn;
  private Person testPerson;
  private PersonsDAO personDAO;
  private UsersDAO userDAO;


  @BeforeEach
  public void SetUp() throws DataAccessException
  {
    clearService.ClearDatabase();
    testPerson = new Person("nobibi", "NOBIBI", "Yiqi", "Wang",
            "M", "dad001", "mom001", "000000");

    conn = db.getConnection();
    personDAO = new PersonsDAO(conn);
    userDAO = new UsersDAO(conn);
  }

  @AfterEach
  public void TearDown() throws DataAccessException
  {
    db.closeConnection(false);
    clearService.ClearDatabase();
  }

  @Test
  void InsertPass() throws DataAccessException {
    personDAO.Insert(testPerson);

    Person compareTest = personDAO.Find(testPerson.getPersonID());

    assertNotNull(compareTest);

    assertEquals(testPerson, compareTest);
  }

  @Test
  void InsertFail() throws DataAccessException {
    personDAO.Insert(testPerson);
    assertThrows(DataAccessException.class, () -> personDAO.Insert(testPerson));
  }


  /*
      testPerson = new Person("nobibi", "NOBIBI", "Yiqi", "Wang",
"M", "dad001", "mom001", "000000");
   */
  @Test
  void DeletePass() throws DataAccessException, SQLException {
    assertNull(personDAO.Find("nobibi"));

    personDAO.Insert(testPerson);
    conn.commit();

    assertEquals("NOBIBI", personDAO.Find("nobibi").getAssociatedUsername());
    personDAO.Delete("NOBIBI");
    conn.commit();
    assertNull(personDAO.Find("nobibi"));
  }

  /*
    testPerson = new Person("nobibi", "NOBIBI", "Yiqi", "Wang",
"M", "dad001", "mom001", "000000");
 */
  @Test
  void DeleteFail() throws DataAccessException, SQLException {
    assertNull(personDAO.Find("nobibi"));

    personDAO.Insert(testPerson);
    conn.commit();

    assertEquals("NOBIBI", personDAO.Find("nobibi").getAssociatedUsername());

    personDAO.Delete("nobibi");
    conn.commit();

    assertNotNull(personDAO.Find("nobibi"));
  }

  @Test
  void ClearPass() {
    try {
      assertNull(personDAO.Find("nobibi"));
      personDAO.Insert(testPerson);
      assertNotNull(personDAO.Find("nobibi"));
      assertTrue(personDAO.Clear());
    } catch (DataAccessException e) {
      System.out.println("Error unable to find the person using the personID\n");
    }
  }

  @Test
  void FindPass() {
    try {
      assertNull(personDAO.Find("nobibi"));
      personDAO.Insert(testPerson);
      assertNotNull(personDAO.Find("nobibi"));

      Person testPerson = personDAO.Find("nobibi");

      assertEquals(testPerson, testPerson);
    } catch (DataAccessException e) {
      System.out.println("Error: unable to find the person\n");
    }
  }

  @Test
  void FindFail() {
    try {
      assertNull(personDAO.Find("nobibi"));
      personDAO.Insert(testPerson);
      assertNotNull(personDAO.Find("nobibi"));
      assertNull(personDAO.Find("gigids"));
    } catch (DataAccessException e) {
      System.out.println("Error: unable to find the person\n");
    }
  }

  @Test
  void FindAllPass() throws SQLException, DataAccessException {
    ArrayList<Person> persons = new ArrayList<Person>();
    ArrayList<Person> findPersons = new ArrayList<>();

    User TestUserOne = new User("nobibi", "password", "nobibi@gmail.com", "Yiqi", "Wang", "M", "nobibi1");

    Person TestPersonOne = new Person("1", "gg", "mark", "sd", "F", "dad002", "mom002", "hus02");
    Person TestPersonTwo = new Person("1-2", "gg", "Bill", "Saggy", "F", null, null, "hus03");
    Person TestPersonThree = new Person("1-2-3", "SB", "Boop", "Saggin", "M", null, "abc1234", null);


    userDAO.Insert(TestUserOne);

    personDAO.Insert(TestPersonOne);
    personDAO.Insert(TestPersonTwo);
    personDAO.Insert(TestPersonThree);
    conn.commit();

    persons.add(TestPersonOne);
    persons.add(TestPersonTwo);


    findPersons = personDAO.FindAll("gg");
    assertNotNull(findPersons);
    assertEquals(2, findPersons.size());

    for (int i = 0; i < persons.size(); i++)
    {
      assertEquals(persons.get(i), findPersons.get(i));
    }
  }

  @Test
  void FindAllFail() throws DataAccessException, SQLException {
    ArrayList<Person> persons = new ArrayList<Person>();
    ArrayList<Person> findPersons = new ArrayList<>();

    User uOne = new User("123", "1234", "123@gmaiil.com", "yi", "qw", "M", "11021");

    Person pTwo = new Person("1-2", "OptimusPrime", "Bill", "Saggy", "F", null, null, null);
    Person pFour = new Person("1-2-3-4", "gg", "Bop", "Sagger", "M", "123abcd", null, null);
    Person pSix = new Person("OptimusPappi", "OptimusPrime", "Oppy", "Poppy", "R", "m", "1234", null);
    Person pEight = new Person("1-2-3-4-5-6-7", "gg", "Dogs", "Yummy", "F", "Lol744", null, null);

    userDAO.Insert(uOne);

    personDAO.Insert(pTwo);
    personDAO.Insert(pFour);
    personDAO.Insert(pSix);
    personDAO.Insert(pEight);

    conn.commit();

    persons.add(pTwo);
    persons.add(pSix);

    findPersons = personDAO.FindAll("gg");
    assertNotNull(findPersons);
    assertEquals(2, findPersons.size());

    for (int i = 0; i < persons.size(); i++) {
      assertNotEquals(persons.get(i), findPersons.get(i));
    }
  }
}


