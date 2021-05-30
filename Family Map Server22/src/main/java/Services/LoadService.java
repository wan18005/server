package Services;

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
  private EventsDAO eventDAO;
  private PersonsDAO personDAO;
  private UsersDAO userDAO;

  private AuthToken authToken = new AuthToken();
  private Event event = new Event();
  private Person person = new Person();
  private User user = new User();

  private ClearService clearService = new ClearService();

  private boolean CheckUsers(User[] users) {
    for (User u : users) {
      if (u.getUsername() == null ||
              u.getPassword() == null ||
              u.getEmail() == null ||
              u.getFirstName() == null ||
              u.getLastName() == null ||
              u.getGender() == null ||
              u.getPersonID() == null) {
        return false;
      }
    }
    return true;
  }

  private boolean CheckPersons(Person[] persons) {
    for (Person p : persons) {
      if (p.getPersonID() == null ||
              p.getAssociatedUsername() == null ||
              p.getFirstName() == null ||
              p.getLastName() == null ||
              p.getGender() == null) {
        return false;
      }
    }
    return true;
  }

  private boolean CheckEvents(Event[] events) {
    for (Event e : events) {
      if (e.getEventID() == null ||
              e.getAssociatedUsername() == null ||
              e.getPersonID() == null ||
              e.getCountry() == null ||
              e.getCity() == null ||
              e.getEventType() == null) {
        return false;
      }
    }
    return true;
  }

  private boolean ValidInput(LoadRequest r)
  {
    if (CheckUsers(r.getUsers()) &&
            CheckPersons(r.getPersons()) &&
            CheckEvents(r.getEvents()))
      return true;
    else
    {
      return false;
    }

  }

  private void InsertUsers(User[] users) throws DataAccessException {
    if (users.length == 0)
    {
      throw new DataAccessException("Error: Users array is empty.");
    }

    for (User u : users)
    {
      if (userDAO.Find(u.getUsername()) == null)
      {
        userDAO.Insert(u);
        authTokenDAO.Insert(new AuthToken(u.getUsername()));
      }
      else
      {
        throw new DataAccessException("Error: User has already been created.");
      }
    }
  }

  private void InsertPersons(Person[] persons) throws DataAccessException {
    if (persons.length == 0) {
      throw new DataAccessException("Error: Persons array is empty.");
    }
    for (Person p : persons)
    {
      if (userDAO.Find(p.getAssociatedUsername()) == null)
      {
        throw new DataAccessException("Error: User does not exist.");
      }
      else if (personDAO.Find(p.getPersonID()) == null)
      {
        personDAO.Insert(p);
      }
      else
      {
        throw new DataAccessException("Error: Potential duplicate persons in database.");
      }
    }
  }

  private void InsertEvents(Event[] events) throws DataAccessException {
    if (events.length == 0)
    {
      throw new DataAccessException("Error: Events array is empty.");
    }
    for (Event e : events)
    {
      if (userDAO.Find(e.getAssociatedUsername()) == null)
      {
        throw new DataAccessException("Error: User does not exist.");
      }
      else if (eventDAO.Find(e.getEventID()) == null)
      {
        eventDAO.Insert(e);
      }
      else
      {
        throw new DataAccessException("Error: Potential duplicate events in database.");
      }
    }
  }

  public LoadResult Load(LoadRequest request) throws DataAccessException
  {
    //variable
    DAO dao = new DAO();
    Connection conn = dao.getConnection();
    authTokenDAO = new AuthTokenDAO(conn);
    eventDAO = new EventsDAO(conn);
    personDAO = new PersonsDAO(conn);
    userDAO = new UsersDAO(conn);

    try
    {
      if (!ValidInput(request))
      {
        return new LoadResult("Error: Invalid input.", false);
      }

      try
      {
        clearService.ClearDatabase();
      }
      catch (DataAccessException failedToClear)
      {
        failedToClear.printStackTrace();
        dao.closeConnection(false);
        return new LoadResult("Error: Failed to clear database before loading.", false);
      }
      InsertUsers(request.getUsers());
      conn.commit();
      InsertPersons(request.getPersons());
      conn.commit();
      InsertEvents(request.getEvents());
      dao.closeConnection(true);

      String successString = "Successfully added " + request.getUsers().length + " users, "
              + request.getPersons().length + " persons, and " + request.getEvents().length + " events.";
      return new LoadResult(successString, true);

    }
    catch (DataAccessException | SQLException e) {
      e.printStackTrace();
      dao.closeConnection(false);
      clearService.ClearDatabase();
      return new LoadResult(e.toString(), false);
    }
  }






}
