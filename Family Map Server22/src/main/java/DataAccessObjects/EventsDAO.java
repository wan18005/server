package DataAccessObjects;

import Models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A class used for accessing the Event table in the database.
 */
public class EventsDAO {
  //var
  private Connection conn;
  //constructor by code/generator
  public EventsDAO(Connection conn) {
    this.conn = conn;
  }
  /**
   * Event ID: Unique identifier for this event (non-empty string)
   * Associated Username: User (Username) to which this person belongs
   * Person ID: ID of person to which this event belongs
   * Latitude: Latitude of event’s location (float)
   * Longitude: Longitude of event’s location (float)
   * Country: Country in which event occurred
   * City: City in which event occurred
   * EventType: Type of event (birth, baptism, christening, marriage, death, etc.)
   * Year: Year in which event occurred (integer)
   */
  /**
   * Inserts the event into the Event table in the database.
   *
   * @param event
   * @return true or error message
   */
  public void Insert(Event event) throws DataAccessException {
    //We can structure our string to be similar to a sql command, but if we insert question
    //marks we can change them later with help from the statement
    String sql = "INSERT INTO Events (EventID, AssociatedUsername, PersonID, Country, City, EventType," +
            "Latitude, Longitude, Year) VALUES(?,?,?,?,?,?,?,?,?)";
    try (PreparedStatement PS = conn.prepareStatement(sql)) {
      PS.setString(1, event.getEventID());
      PS.setString(2, event.getAssociatedUsername());
      PS.setString(3, event.getPersonID());
      PS.setString(4, event.getCountry());
      PS.setString(5, event.getCity());
      PS.setString(6, event.getEventType());
      PS.setFloat(7, event.getLatitude());
      PS.setFloat(8, event.getLongitude());
      PS.setInt(9, event.getYear());
      // update the changes
      PS.executeUpdate();
    } catch (SQLException e)
    {
      throw new DataAccessException("Error while inserting into Event table");
    }
  }

  /**
   * Delete the Event table by the given AssociatedUserName
   *
   * @return true or false.
   */
  public Boolean Delete(String associatedUsername)
  {
    String sql = "DELETE FROM Events WHERE AssociatedUsername = ?;";

    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, associatedUsername);
      stmt.executeUpdate();

    }
    catch (SQLException e)
    {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * Clears the Event table in the database.
   * @return true or false.
   */
  public Boolean Clear() {
    boolean clearStatues = false;
    String sql = "DROP TABLE IF EXISTS Events;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      //update
      stmt.executeUpdate();
      // clear is success return ture;
      clearStatues = true;
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("\"Error Clearing Event's Table, return false\n");
    }
    return clearStatues;
  }

  /**
   * find the Event table in the database.
   * @return true or false.
   */
  public Event Find(String eventID) throws DataAccessException {
    Event event;
    //make sure the Result set is empty before anything.
    ResultSet rs = null;

    String sql = "SELECT * FROM Events WHERE EventID = ?;";

    try (PreparedStatement PS = conn.prepareStatement(sql)) {
      PS.setString(1, eventID);
      rs = PS.executeQuery();
      if (rs.next()) {
        event = new Event(rs.getString("EventID"), rs.getString("AssociatedUsername"),
                rs.getString("PersonID"), rs.getString("Country"), rs.getString("City"),
                rs.getString("EventType"), rs.getFloat("Latitude"), rs.getFloat("Longitude"),
                rs.getInt("Year"));
        return event;
      }

    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding event");
    } finally {
      if (rs != null) {
        try {
          rs.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }

    }
    return null;
  }
  /**
   * find the all the with the given username in Event table in the database.
   * @return
   */
  public ArrayList<Event> FindAll(String username) throws DataAccessException {
    //using to store the event that is found
    ArrayList<Event> events = new ArrayList<Event>();
    //Event event;

    ResultSet resultSet = null;
    String sql = "SELECT * FROM Events WHERE AssociatedUsername = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      resultSet = stmt.executeQuery();
      while (resultSet.next())
      {/*
      PS.setString(1, event.getEventID());
      PS.setString(2, event.getAssociatedUserName());
      PS.setString(3, event.getPersonID());
      PS.setString(4, event.getCountry());
      PS.setString(5, event.getCity());
      PS.setString(6, event.getEventType());
      PS.setFloat(7, event.getLatitude());
      PS.setFloat(8, event.getLongitude());
      PS.setInt(9, event.getYear());
      */
        String eventID = resultSet.getString(1);
        String associatedUsername = resultSet.getString(2);
        String personID = resultSet.getString(3);
        String country = resultSet.getString(4);
        String city = resultSet.getString(5);
        String eventType = resultSet.getString(6);
        float latitude = resultSet.getFloat(7);
        float longitude = resultSet.getFloat(8);
        Integer year = resultSet.getInt(9);
        Event event = new Event(eventID,associatedUsername,personID,country,city,eventType,latitude,longitude,year);

        events.add(event);
      }

    } catch (SQLException e)
    {
      e.printStackTrace();
      throw new DataAccessException("Error while finding event with associatedUserName");
    } finally
    {
      if (resultSet != null)
      {
        try
        {
          resultSet.close();
        }
        catch (SQLException e)
        {
          e.printStackTrace();
        }
      }
    }
    if (events.size() == 0) {
      return null;
    } else {
      return events;
    }
  }
}
