package Service.Services;

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
  private DAO db;
  private Connection conn;
  private AuthTokenDAO authTokenDAO;
  private EventsDAO eventsDAO;


  public EventResult event(String authtoken) throws DataAccessException {
    db = new DAO();
    conn = db.getConnection();
    authTokenDAO = new AuthTokenDAO(conn);
    eventsDAO = new EventsDAO(conn);

    try {
      AuthToken findToken = authTokenDAO.Find(authtoken);
      if (findToken == null) {
        db.closeConnection(false);
        return new EventResult("Error: Invalid authtoken returned null.", false);
      } else {
        ArrayList<Event> events = eventsDAO.FindAll(findToken.getUsername());
        if (events == null) {
          db.closeConnection(false);
          return new EventResult("Error: Person is not associated with " + findToken.getUsername() + ".", false);
        } else {
          db.closeConnection(true);
          return new EventResult(events);
        }
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
      db.closeConnection(false);
      return new EventResult("Error: Database fatal error.", false);
    }
  }

  public EventResult event(String eventID, String authtoken) throws DataAccessException {
    db = new DAO();
    conn = db.getConnection();
    authTokenDAO = new AuthTokenDAO(conn);
    eventsDAO = new EventsDAO(conn);

    try {
      AuthToken findToken = authTokenDAO.Find(authtoken);
      if (findToken == null) {
        db.closeConnection(false);
        return new EventResult("Error: Invalid authtoken returned null.", false);
      } else {
        Event findEvent = eventsDAO.Find(eventID);
        if (findEvent == null) {
          db.closeConnection(false);
          return new EventResult("Error: Event does not exist.", false);
        } else if (! findToken.getUsername().equals(findEvent.getAssociatedUsername())) {
          db.closeConnection(false);
          return new EventResult("Error: Event is not associated with " + findToken.getUsername() + ".", false);
        } else {
          db.closeConnection(true);
          return new EventResult(findEvent, findToken.getUsername());
        }
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
      db.closeConnection(false);
      return new EventResult("Error: Database fatal error.", false);
    }
  }
}
