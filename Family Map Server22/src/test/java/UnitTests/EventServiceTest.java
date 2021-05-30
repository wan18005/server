package UnitTests;

import DataAccessObjects.*;
import Models.*;
import Results.EventResult;
import Services.ClearService;
import Services.EventService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;


public class EventServiceTest
{

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

    authTokenDAO.Insert(new AuthToken("nobibi", "1111"));
    authTokenDAO.Insert(new AuthToken("yirui", "2222"));
    conn.commit();
  }

  @AfterEach
  public void tearDown() throws DataAccessException {
    clearService.ClearDatabase();
    db.closeConnection(false);
  }

  @Test
  public void EventPass() throws SQLException, DataAccessException {
    Event eOne = new Event("1", "nobibi", "gg", "China", "ew", "22", 1, 2, 111);
    Event eTwo = new Event("12", "nobibi", "Yoggu", "China", "we", "22", 12, 23, 222);
    Event eThree = new Event("123", "nobibi", "gg", "China", "ew", "22", 123, 234, 333);
    Event eFour = new Event("1234", "yirui", "gg", "China", "ew", "22", 1234, 2345, 444);
    Event eFive = new Event("12345", "nobibi", "gg", "China", "ew", "22", 12345, 23456, 555);
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
    assertEquals("nobibi", eventResult.getAssociatedUsername());

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
    Event eOne = new Event("1", "nobibi", "1", "1", "1", "1", 1, 2, 111);
    Event eTwo = new Event("1-2", "nobibi", "2", "2", "2", "2", 12, 23, 222);
    Event eThree = new Event("1-2-3", "nobibi", "3", "3", "3", "3", 123, 234, 333);
    Event eFour = new Event("1-2-3-4", "gg", "4", "4", "4", "4", 1234, 2345, 444);
    Event eFive = new Event("1-2-3-4-5", "nobibi", "5", "5", "5", "5", 12345, 23456, 555);
    eventDAO.Insert(eOne);
    eventDAO.Insert(eTwo);
    eventDAO.Insert(eThree);
    eventDAO.Insert(eFour);
    eventDAO.Insert(eFive);
    conn.commit();

    eventResult = eventService.event("23", "1111");

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
    assertEquals("Error: Person is not associated with yirui.", eventResult.getMessage());
  }
}
