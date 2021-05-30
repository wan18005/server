package Service.Services;

import DataAccessObjects.*;
import Models.AuthToken;
import Models.Person;
import Models.User;
import Results.*;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Class that will handle Person business logic and determine if a request was successful.
 */
public class PersonService {
  private AuthTokenDAO authTokenDAO;
  private PersonsDAO personsDAO;
  private DAO db;
  private Connection conn;


  public PersonResult Person(String personID, String authToken) throws DataAccessException {
    db = new DAO();
    conn = db.getConnection();
    authTokenDAO = new AuthTokenDAO(conn);
    personsDAO = new PersonsDAO(conn);

    try {
      AuthToken findToken = authTokenDAO.Find(authToken);
      Person findPerson = personsDAO.Find(personID);

      if (findToken == null) {
        db.closeConnection(false);
        return new PersonResult("Error: Invalid authToken returned null.", false);
      } else {
        if (findPerson == null) {
          db.closeConnection(false);
          return new PersonResult("Error: Person was not found in the database.", false);
        } else if (! findToken.getUsername().equals(findPerson.getAssociatedUsername())) {
          db.closeConnection(false);
          return new PersonResult("Error: Person is not associated with " + findToken.getUsername() + ".", false);
        } else {
          db.closeConnection(true);
          return new PersonResult(findPerson);
        }
      }
    } catch (DataAccessException e) {
      db.closeConnection(false);
      return new PersonResult(e.toString(), false);
    }
  }

  public PersonResult Person(String authToken) throws DataAccessException {
    db = new DAO();
    conn = db.getConnection();
    authTokenDAO = new AuthTokenDAO(conn);
    personsDAO = new PersonsDAO(conn);

    try {
      AuthToken findToken = authTokenDAO.Find(authToken);

      if (findToken == null) {
        db.closeConnection(false);
        return new PersonResult("Error: AuthToken returned null.", false);
      } else {
        ArrayList<Person> persons = personsDAO.FindAll(findToken.getUsername());
        if (persons == null) {
          db.closeConnection(false);
          return new PersonResult("Error: " + findToken.getUsername() + " has no associated Persons.", false);
        } else {
          Person personsPerson = FindPerson(findToken);
          db.closeConnection(true);
          return new PersonResult(persons, personsPerson);
        }
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
      db.closeConnection(false);
      return new PersonResult(e.toString(), false);
    }
  }

  private Person FindPerson(AuthToken t) throws DataAccessException {
    UsersDAO uDAO = new UsersDAO(conn);
    User temp = uDAO.Find(t.getUsername());
    return personsDAO.Find(temp.getPersonID());
  }
}
