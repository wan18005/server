package Services;

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
public class PersonService
{
  private AuthTokenDAO authTokenDAO;
  private PersonsDAO personDAO;
  private DAO dao;
  private Connection connection;

  private Person FindPerson(AuthToken t) throws DataAccessException
  {
    UsersDAO usersDAO = new UsersDAO(connection);
    User temp = usersDAO.Find(t.getUsername());
    return personDAO.Find(temp.getPersonID());
  }

  public PersonResult Person(String authToken) throws DataAccessException
  {
    dao = new DAO();
    connection = dao.getConnection();
    authTokenDAO = new AuthTokenDAO(connection);
    personDAO = new PersonsDAO(connection);

    try
    {
      AuthToken findToken = authTokenDAO.Find(authToken);

      if (findToken == null) {
        dao.closeConnection(false);
        return new PersonResult("Error: AuthToken returned null.", false);
      } else {
        ArrayList<Person> persons = personDAO.FindAll(findToken.getUsername());
        if (persons == null) {
          dao.closeConnection(false);
          return new PersonResult("Error: " + findToken.getUsername() + " has no associated Persons.", false);
        } else {
          Person personsPerson = FindPerson(findToken);
          dao.closeConnection(true);
          return new PersonResult(persons, personsPerson);
        }
      }
    }
    catch (DataAccessException e) {
      e.printStackTrace();
      dao.closeConnection(false);
      return new PersonResult(e.toString(), false);
    }
  }

  public PersonResult Person(String personID, String authToken) throws DataAccessException
  {
    dao = new DAO();
    connection = dao.getConnection();
    authTokenDAO = new AuthTokenDAO(connection);
    personDAO = new PersonsDAO(connection);

    try {
      AuthToken findToken = authTokenDAO.Find(authToken);
      Person findPerson = personDAO.Find(personID);

      if (findToken == null)
      {
        dao.closeConnection(false);
        return new PersonResult("Error: Invalid authToken returned null.", false);
      } else
      {
        if (findPerson == null)
        {
          dao.closeConnection(false);
          return new PersonResult("Error: Person was not found in the database.", false);
        } else if (! findToken.getUsername().equals(findPerson.getAssociatedUsername()))
        {
          dao.closeConnection(false);
          return new PersonResult("Error: Person is not associated with " + findToken.getUsername() + ".", false);
        } else
        {
          dao.closeConnection(true);
          return new PersonResult(findPerson);
        }
      }
    } catch (DataAccessException e)
    {
      dao.closeConnection(false);
      return new PersonResult(e.toString(), false);
    }
  }
}
