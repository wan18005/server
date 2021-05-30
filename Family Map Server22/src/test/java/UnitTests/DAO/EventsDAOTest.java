package UnitTests;

import DataAccessObjects.*;
import Models.*;

import Service.Services.ClearService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class EventsDAOTest {

  private ClearService clearService = new ClearService();
  private DAO db = new DAO();

  private Connection conn;
  private Event bestEvent;
  private EventsDAO eventDAO;

  @BeforeEach
  public void SetUp() throws DataAccessException {
    clearService.ClearDatabase();

    bestEvent = new Event("Biking_123A", "Gale", "Gale123A", "Japan", "Ushiku",
            "Biking_Around", 35.9f, 140.1f, 2016);

    conn = db.getConnection();
    eventDAO = new EventsDAO(conn);
  }

  @AfterEach
  public void TearDown() throws DataAccessException {
    db.closeConnection(false);
    clearService.ClearDatabase();

  }

  @Test
  public void InsertPass() throws DataAccessException {
    eventDAO.Insert(bestEvent);

    Event findEvent = eventDAO.Find(bestEvent.getEventID());

    assertNotNull(findEvent);

    assertEquals(bestEvent, findEvent);
  }

  @Test
  public void InsertFail() throws DataAccessException {
    eventDAO.Insert(bestEvent);
    assertThrows(DataAccessException.class, () -> eventDAO.Insert(bestEvent));
  }

  @Test
  public void FindEventPass() throws DataAccessException {
    Event eOne = new Event("1", "OptimusPrime", "Can", "Russia", "Smell", "Mustache", 1, 2, 111);

    eventDAO.Insert(eOne);
    Event findEvent = eventDAO.Find("1");
    assertEquals(findEvent, eOne);
  }

  @Test
  public void FindEventFail() throws DataAccessException, SQLException {

    Event eOne = new Event("1", "OptimusPrime", "Can", "Russia", "Smell", "Mustache", 1, 2, 111);
    eventDAO.Insert(eOne);
    conn.commit();
    assertNull(eventDAO.Find("1-2"));
  }

  @Test
  public void FindAllEventsPass() throws DataAccessException, SQLException {
    ArrayList<Event> events = new ArrayList<Event>();
    ArrayList<Event> findEvents;

    Event eOne = new Event("1", "OptimusPrime", "Can", "Russia", "Smell", "Mustache", 1, 2, 111);
    Event eTwo = new Event("1-2", "OptimusPrime", "You", "Ussiar", "Stink", "HandelBar", 12, 23, 222);
    Event eThree = new Event("1-2-3", "OptimusPrime", "Feel", "Ssiaru", "Stankin", "Curler", 123, 234, 333);
    Event eFive = new Event("1-2-3-4-5", "OptimusPrime", "Love", "Iaruss", "VStank", "Laughed", 12345, 23456, 555);
    eventDAO.Insert(eOne);
    eventDAO.Insert(eTwo);
    eventDAO.Insert(eThree);
    eventDAO.Insert(eFive);
    conn.commit();


    events.add(eOne);
    events.add(eTwo);
    events.add(eThree);
    events.add(eFive);

    try {
      clearService.ClearDatabase();
      eventDAO.Insert(eOne);
      eventDAO.Insert(eTwo);
      eventDAO.Insert(eThree);
      eventDAO.Insert(eFive);

      findEvents = eventDAO.FindAll("OptimusPrime");

      for (int i = 0; i < events.size(); i++) {
        assertEquals(events.get(i), findEvents.get(i));
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void FindAllEventsFail() throws DataAccessException, SQLException {
    ArrayList<Event> events = new ArrayList<Event>();
    ArrayList<Event> findEvents;

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


    events.add(eOne);
    events.add(eTwo);
    events.add(eThree);
    events.add(eFour);
    events.add(eFive);

    try {
      clearService.ClearDatabase();
      eventDAO.Insert(eOne);
      eventDAO.Insert(eTwo);
      eventDAO.Insert(eThree);
      eventDAO.Insert(eFour);
      eventDAO.Insert(eFive);

      findEvents = eventDAO.FindAll("OptimusPrime");

      for (int i = 0; i < 4; i++) {
        if (events.get(i) == eFour) {
          assertNotEquals(events.get(i), findEvents.get(i));
        } else {
          assertEquals(events.get(i), findEvents.get(i));
        }
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
    }
  }
}
