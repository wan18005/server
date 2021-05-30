package Service.Services;

import DataAccessObjects.*;
import Models.AuthToken;
import Models.Event;
import Models.Person;
import Models.User;
import Results.*;
import Requests.*;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class that will handle Load business logic and determine if a request was successful.
 */
public class LoadService {

  private AuthTokenDAO authTokenDAO;
  private EventsDAO eventsDAO;
  private PersonsDAO personsDAO;
  private UsersDAO usersDAO;

  private AuthToken authToken = new AuthToken();
  private Event event = new Event();
  private Person person = new Person();
  private User user = new User();

  private ClearService clearService = new ClearService();

  /**
   * Function that will load clear all data form the database and then load the user, person, and event into the database.
   *
   * @param r the request passed in
   * @return the result of the operation.
   */
  public LoadResult Load(LoadRequest r) throws DataAccessException {
    DAO db = new DAO();
    Connection conn = db.getConnection();

    authTokenDAO = new AuthTokenDAO(conn);
    eventsDAO = new EventsDAO(conn);
    personsDAO = new PersonsDAO(conn);
    usersDAO = new UsersDAO(conn);

    try {
      if (! ValidInput(r)) {
        return new LoadResult("Error: Invalid input.", false);
      }

      try {
        clearService.ClearDatabase();
      } catch (DataAccessException failedToClear) {
        failedToClear.printStackTrace();
        db.closeConnection(false);
        return new LoadResult("Error: Failed to clear database before loading.", false);
      }

      InsertUsers(r.getUsers());
      conn.commit();
      InsertPersons(r.getPersons());
      conn.commit();
      InsertEvents(r.getEvents());

      db.closeConnection(true);

      String successString = "Successfully added " +
              r.getUsers().length + " users, " +
              r.getPersons().length + " persons, and " +
              r.getEvents().length + " events.";

      return new LoadResult(successString, true);

    } catch (DataAccessException | SQLException e) {
      e.printStackTrace();
      db.closeConnection(false);
      clearService.ClearDatabase();
      return new LoadResult(e.toString(), false);
    }
  }

  private boolean ValidInput(LoadRequest r) {
    return (CheckUsers(r.getUsers()) &&
            CheckPersons(r.getPersons()) &&
            CheckEvents(r.getEvents()));
  }

  private boolean CheckUsers(User[] users) {
    for (User temp : users) {
      if (temp.getUsername() == null ||
              temp.getPassword() == null ||
              temp.getEmail() == null ||
              temp.getFirstName() == null ||
              temp.getLastName() == null ||
              temp.getGender() == null ||
              temp.getPersonID() == null) {
        return false;
      }
    }
    return true;
  }

  private boolean CheckPersons(Person[] persons) {
    for (Person temp : persons) {
      if (temp.getPersonID() == null ||
              temp.getAssociatedUsername() == null ||
              temp.getFirstName() == null ||
              temp.getLastName() == null ||
              temp.getGender() == null) {
        return false;
      }
    }
    return true;
  }

  private boolean CheckEvents(Event[] events) {
    for (Event temp : events) {
      if (temp.getEventID() == null ||
              temp.getAssociatedUsername() == null ||
              temp.getPersonID() == null ||
              temp.getCountry() == null ||
              temp.getCity() == null ||
              temp.getEventType() == null) {
        return false;
      }
    }
    return true;
  }

  private void InsertUsers(User[] users) throws DataAccessException {
    if (users.length == 0) {
      throw new DataAccessException("Error: Users array is empty.");
    }

    for (User temp : users) {
      if (usersDAO.Find(temp.getUsername()) == null) {
        usersDAO.Insert(temp);
        authTokenDAO.Insert(new AuthToken(temp.getUsername()));
      } else {
        throw new DataAccessException("Error: User has already been created.");
      }
    }
  }

  private void InsertPersons(Person[] persons) throws DataAccessException {
    if (persons.length == 0) {
      throw new DataAccessException("Error: Persons array is empty.");
    }

    for (Person temp : persons) {

      if (usersDAO.Find(temp.getAssociatedUsername()) == null) {
        throw new DataAccessException("Error: User does not exist.");
      } else if (personsDAO.Find(temp.getPersonID()) == null) {
        personsDAO.Insert(temp);
      } else {
        throw new DataAccessException("Error: Potential duplicate persons in database.");
      }
    }
  }

  private void InsertEvents(Event[] events) throws DataAccessException {
    if (events.length == 0) {
      throw new DataAccessException("Error: Events array is empty.");
    }

    for (Event temp : events) {
      if (usersDAO.Find(temp.getAssociatedUsername()) == null) {
        throw new DataAccessException("Error: User does not exist.");
      } else if (eventsDAO.Find(temp.getEventID()) == null) {
        eventsDAO.Insert(temp);
      } else {
        throw new DataAccessException("Error: Potential duplicate events in database.");
      }
    }
  }
}
