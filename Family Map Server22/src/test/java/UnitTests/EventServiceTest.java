package UnitTests;

import DataAccessObjects.*;
import Models.*;
import Results.EventResult;
import Service.Services.ClearService;
import Service.Services.EventService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class EventServiceTest {
  ClearService clearService = new ClearService();
  EventService eventService = new EventService();
  DAO db = new DAO();

  EventResult eventResult;
  AuthTokenDAO authTokenDAO;
  EventsDAO eventDAO;
  Connection conn;

  @BeforeEach
  public void setUp() throws SQLException, DataAccessException {
    clearService.ClearDatabase();
    conn = db.getConnection();

    authTokenDAO = new AuthTokenDAO(conn);
    eventDAO = new EventsDAO(conn);

    authTokenDAO.Insert(new AuthToken("OptimusPrime", "1111"));
    authTokenDAO.Insert(new AuthToken("Megatron", "2222"));
    conn.commit();
  }

  @AfterEach
  public void tearDown() throws DataAccessException {
    clearService.ClearDatabase();
    db.closeConnection(false);
  }

  @Test
  public void EventPass() throws SQLException, DataAccessException {
    Event eOne = new Event("1", "OptimusPrime", "Can", "Russia", "Smell", "Mustache", 1, 2, 111);
    Event eTwo = new Event("1-2", "OptimusPrime", "You", "Ussiar", "Stink", "HandelBar", 12, 23, 222);
    Event eThree = new Event("1-2-3", "OptimusPrime", "Feel", "Ssiaru", "Stankin", "Curler", 123, 234, 333);
    Event eFour = new Event("1-2-3-4", "Megatron", "The", "Siarus", "PooStink", "HairDryer", 1234, 2345, 444);
    Event eFive = new Event("1-2-3-4-5", "OptimusPrime", "Love", "Iaruss", "VStank", "Laughed", 12345, 23456, 555);
    eventDAO.Insert(eOne);
    eventDAO.Insert(eTwo);
    eventDAO.Insert(eThree);
    eventDAO.Insert(eFour);
    eventDAO.Insert(eFive);
    conn.commit();

    eventResult = eventService.event("1", "1111");

    assertNotNull(eventResult.getEventID());
    assertNotNull(eventResult.getAssociatedUsername());
    assertNotNull(eventResult.getCity());
    assertNotNull(eventResult.getCountry());
    assertNotNull(eventResult.getEventType());
    assertEquals(1, eventResult.getLatitude());
    assertEquals(2, eventResult.getLongitude());
    assertEquals(111, eventResult.getYear());
    assertNull(eventResult.getMessage());
    assertEquals("OptimusPrime", eventResult.getAssociatedUsername());

    eventResult = eventService.event("1111");

    assertNotNull(eventResult.getData());
    assertEquals(eventResult.getData().size(), 4);
    assertNull(eventResult.getMessage());

    ArrayList<Event> eventArray = new ArrayList<Event>();

    eventArray.add(eOne);
    eventArray.add(eTwo);
    eventArray.add(eThree);
    eventArray.add(eFive);

    for (int i = 0; i < eventArray.size(); i++) {
      assertEquals(eventArray.get(i), eventResult.getData().get(i));
    }
  }

  @Test
  public void EventFail() throws SQLException, DataAccessException {
    Event eOne = new Event("1", "OptimusPrime", "Can", "Russia", "Smell", "Mustache", 1, 2, 111);
    Event eTwo = new Event("1-2", "OptimusPrime", "You", "Ussiar", "Stink", "HandelBar", 12, 23, 222);
    Event eThree = new Event("1-2-3", "OptimusPrime", "Feel", "Ssiaru", "Stankin", "Curler", 123, 234, 333);
    Event eFour = new Event("1-2-3-4", "Bumblebee", "The", "Siarus", "PooStink", "HairDryer", 1234, 2345, 444);
    Event eFive = new Event("1-2-3-4-5", "OptimusPrime", "Love", "Iaruss", "VStank", "Laughed", 12345, 23456, 555);
    eventDAO.Insert(eOne);
    eventDAO.Insert(eTwo);
    eventDAO.Insert(eThree);
    eventDAO.Insert(eFour);
    eventDAO.Insert(eFive);
    conn.commit();

    eventResult = eventService.event("Bumblebee", "1111");

    assertNull(eventResult.getEventID());
    assertNull(eventResult.getAssociatedUsername());
    assertNull(eventResult.getCity());
    assertNull(eventResult.getCountry());
    assertNull(eventResult.getEventType());
    assertNull(eventResult.getLatitude());
    assertNull(eventResult.getLongitude());
    assertNull(eventResult.getYear());
    assertNotNull(eventResult.getMessage());
    assertNull(eventResult.getAssociatedUsername());
    assertEquals("Error: Event does not exist.", eventResult.getMessage());

    eventResult = eventService.event("2222");

    assertNull(eventResult.getData());
    assertNotNull(eventResult.getMessage());
    assertEquals("Error: Person is not associated with Megatron.", eventResult.getMessage());
  }
}
