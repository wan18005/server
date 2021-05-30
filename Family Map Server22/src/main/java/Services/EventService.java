package Services;

import DataAccessObjects.AuthTokenDAO;
import DataAccessObjects.DataAccessException;
import DataAccessObjects.DAO;
import DataAccessObjects.EventsDAO;
import Models.Event;
import Results.*;
import Models.AuthToken;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Class that will handle Event business logic and determine if a request was successful.
 */
public class EventService {
  private DAO dao;
  private Connection connection;
  private AuthTokenDAO authTokenDAO;
  private EventsDAO eventDAO;


  public EventResult event(String authtoken) throws DataAccessException
  {
    //var
    dao = new DAO();
    connection = dao.getConnection();
    authTokenDAO = new AuthTokenDAO(connection);
    eventDAO = new EventsDAO(connection);

    try {
      AuthToken findToken = authTokenDAO.Find(authtoken);
      if (findToken == null) {
        dao.closeConnection(false);
        return new EventResult("Error: Invalid authtoken returned null.", false);
      } else {
        ArrayList<Event> events = eventDAO.FindAll(findToken.getUsername());
        if (events == null) {
          dao.closeConnection(false);
          return new EventResult("Error: Person is not associated with " + findToken.getUsername() + ".", false);
        } else {
          dao.closeConnection(true);
          return new EventResult(events);
        }
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
      dao.closeConnection(false);
      return new EventResult("Error: Database fatal error.", false);
    }
  }

  public EventResult event(String eventID, String authtoken) throws DataAccessException {
    dao = new DAO();
    connection = dao.getConnection();
    authTokenDAO = new AuthTokenDAO(connection);
    eventDAO = new EventsDAO(connection);

    try {
      AuthToken findToken = authTokenDAO.Find(authtoken);
      if (findToken == null) {
        dao.closeConnection(false);
        return new EventResult("Error: Invalid authtoken returned null.", false);
      } else {
        Event findEvent = eventDAO.Find(eventID);
        if (findEvent == null) {
          dao.closeConnection(false);
          return new EventResult("Error: Event does not exist.", false);
        } else if (! findToken.getUsername().equals(findEvent.getAssociatedUsername())) {
          dao.closeConnection(false);
          return new EventResult("Error: Event is not associated with " + findToken.getUsername() + ".", false);
        } else {
          dao.closeConnection(true);
          return new EventResult(findEvent, findToken.getUsername());
        }
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
      dao.closeConnection(false);
      return new EventResult("Error: Database fatal error.", false);
    }
  }
}
