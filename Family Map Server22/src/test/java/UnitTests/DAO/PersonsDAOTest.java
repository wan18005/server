package UnitTests;

import DataAccessObjects.DataAccessException;
import DataAccessObjects.DAO;
import DataAccessObjects.PersonsDAO;

import DataAccessObjects.UsersDAO;
import Models.Person;

import Models.User;
import Service.Services.ClearService;
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
  private Person bestPerson;
  private PersonsDAO personDAO;
  private UsersDAO userDAO;


  @BeforeEach
  public void SetUp() throws DataAccessException {
    clearService.ClearDatabase();
    bestPerson = new Person("Optimus123", "OptimusPrime123", "Optimus", "Prime",
            "M", "OptimusPapa001", "OptimusMama001", "000000");

    conn = db.getConnection();
    personDAO = new PersonsDAO(conn);
    userDAO = new UsersDAO(conn);
  }

  @AfterEach
  public void TearDown() throws DataAccessException {
    db.closeConnection(false);
    clearService.ClearDatabase();
  }

  @Test
  void InsertPass() throws DataAccessException {
    personDAO.Insert(bestPerson);

    Person compareTest = personDAO.Find(bestPerson.getPersonID());

    assertNotNull(compareTest);

    assertEquals(bestPerson, compareTest);
  }

  @Test
  void InsertFail() throws DataAccessException {
    personDAO.Insert(bestPerson);
    assertThrows(DataAccessException.class, () -> personDAO.Insert(bestPerson));
  }

  @Test
  void DeletePass() throws DataAccessException, SQLException {
    assertNull(personDAO.Find("Optimus123"));

    personDAO.Insert(bestPerson);
    conn.commit();

    assertEquals("OptimusPrime123", personDAO.Find("Optimus123").getAssociatedUsername());
    personDAO.Delete("OptimusPrime123");
    conn.commit();
    assertNull(personDAO.Find("Optimus123"));
  }

  @Test
  void DeleteFail() throws DataAccessException, SQLException {
    assertNull(personDAO.Find("Optimus123"));

    personDAO.Insert(bestPerson);
    conn.commit();

    assertEquals("OptimusPrime123", personDAO.Find("Optimus123").getAssociatedUsername());

    personDAO.Delete("Optimus123");
    conn.commit();

    assertNotNull(personDAO.Find("Optimus123"));
  }

  @Test
  void ClearPass() {
    try {
      assertNull(personDAO.Find("Optimus123"));
      personDAO.Insert(bestPerson);
      assertNotNull(personDAO.Find("Optimus123"));
      assertTrue(personDAO.Clear());
    } catch (DataAccessException e) {
      System.out.println("Error encountered while finding Person\n");
    }
  }

  @Test
  void FindPass() {
    try {
      assertNull(personDAO.Find("Optimus123"));
      personDAO.Insert(bestPerson);
      assertNotNull(personDAO.Find("Optimus123"));

      Person testPerson = personDAO.Find("Optimus123");

      assertEquals(bestPerson, testPerson);
    } catch (DataAccessException e) {
      System.out.println("Error encountered while finding Person\n");
    }
  }

  @Test
  void FindFail() {
    try {
      assertNull(personDAO.Find("Optimus123"));
      personDAO.Insert(bestPerson);
      assertNotNull(personDAO.Find("Optimus123"));
      assertNull(personDAO.Find("Doesn'tExist"));
    } catch (DataAccessException e) {
      System.out.println("Error encountered while finding Person\n");
    }
  }

  @Test
  void FindAllPass() throws SQLException, DataAccessException {
    ArrayList<Person> persons = new ArrayList<Person>();
    ArrayList<Person> findPersons = new ArrayList<>();

    User uOne = new User("Megatron", "autobots", "stars@yahoo", "Optimus", "Prime", "R", "OptimusPappi");

    Person pOne = new Person("1", "OptimusPrime", "Bob", "Saggit", "F", "123abc", "abc123", null);
    Person pTwo = new Person("1-2", "OptimusPrime", "Bill", "Saggy", "F", null, null, null);
    Person pThree = new Person("1-2-3", "OptimusPrime", "Boop", "Saggin", "M", null, "abc1234", null);
    Person pFour = new Person("1-2-3-4", "Megatron", "Bop", "Sagger", "M", "123abcd", null, null);
    Person pFive = new Person("1-2-3-4-5", "Megatron", "Pob", "Reggas", "M", "dcba321", null, null);
    Person pSix = new Person("OptimusPappi", "OptimusPrime", "Oppy", "Poppy", "R", "m", "1234", null);

    userDAO.Insert(uOne);
    personDAO.Insert(pOne);
    personDAO.Insert(pTwo);
    personDAO.Insert(pThree);
    personDAO.Insert(pFour);
    personDAO.Insert(pFive);
    personDAO.Insert(pSix);
    conn.commit();

    persons.add(pOne);
    persons.add(pTwo);
    persons.add(pThree);
    persons.add(pSix);

    findPersons = personDAO.FindAll("OptimusPrime");
    assertNotNull(findPersons);
    assertEquals(4, findPersons.size());

    for (int i = 0; i < persons.size(); i++) {
      assertEquals(persons.get(i), findPersons.get(i));
    }
  }

  @Test
  void FindAllFail() throws DataAccessException, SQLException {
    ArrayList<Person> persons = new ArrayList<Person>();
    ArrayList<Person> findPersons = new ArrayList<>();

    User uOne = new User("Megatron", "autobots", "stars@yahoo", "Optimus", "Prime", "R", "OptimusPappi");

    Person pOne = new Person("1", "OptimusPrime", "Bob", "Saggit", "F", "123abc", "abc123", null);
    Person pTwo = new Person("1-2", "OptimusPrime", "Bill", "Saggy", "F", null, null, null);
    Person pThree = new Person("1-2-3", "OptimusPrime", "Boop", "Saggin", "M", null, "abc1234", null);
    Person pFour = new Person("1-2-3-4", "Megatron", "Bop", "Sagger", "M", "123abcd", null, null);
    Person pFive = new Person("1-2-3-4-5", "Megatron", "Pob", "Reggas", "M", "dcba321", null, null);
    Person pSix = new Person("OptimusPappi", "OptimusPrime", "Oppy", "Poppy", "R", "m", "1234", null);
    Person pSeven = new Person("1-2-3-4-5-6", "Megatron", "Chilly", "Are", "F", "1029Boo", null, null);
    Person pEight = new Person("1-2-3-4-5-6-7", "Megatron", "Dogs", "Yummy", "F", "Lol744", null, null);

    userDAO.Insert(uOne);
    personDAO.Insert(pOne);
    personDAO.Insert(pTwo);
    personDAO.Insert(pThree);
    personDAO.Insert(pFour);
    personDAO.Insert(pFive);
    personDAO.Insert(pSix);
    personDAO.Insert(pSeven);
    personDAO.Insert(pEight);
    conn.commit();

    persons.add(pOne);
    persons.add(pTwo);
    persons.add(pThree);
    persons.add(pSix);

    findPersons = personDAO.FindAll("Megatron");
    assertNotNull(findPersons);
    assertEquals(4, findPersons.size());

    for (int i = 0; i < persons.size(); i++) {
      assertNotEquals(persons.get(i), findPersons.get(i));
    }
  }
}


