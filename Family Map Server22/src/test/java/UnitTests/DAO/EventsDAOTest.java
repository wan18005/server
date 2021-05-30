package UnitTests.DAO;

import DataAccessObjects.*;
import Models.*;

import Services.ClearService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class EventsDAOTest {
  //declare all the var
  private ClearService clearService = new ClearService();
  private DAO db = new DAO();

  private Connection connection;
  private Event TestEvent;
  private EventsDAO eventDAO;
//before each test start
  @BeforeEach
  public void SetUp() throws DataAccessException {
    // clear the dataabse
    clearService.ClearDatabase();
    // test event
    TestEvent = new Event("Walk", "NoBIBI", "nobibi", "Japan", "Tokyo",
            "Walking", 24.2f, 11.1f, 2012);
    //connect to database
    connection = db.getConnection();
    // set the Event right
    eventDAO = new EventsDAO(connection);
  }

  @AfterEach
  public void TearDown() throws DataAccessException {
    //close the connection
    db.closeConnection(false);
    // clear the SQL table
    clearService.ClearDatabase();

  }

  @Test
  public void InsertPass() throws DataAccessException {
    eventDAO.Insert(TestEvent);

    Event findEvent = eventDAO.Find(TestEvent.getEventID());
    // if it is not null
    assertNotNull(findEvent);
    // if it is equal pass
    assertEquals(TestEvent, findEvent);
  }

  @Test
  public void InsertFail() throws DataAccessException {
    eventDAO.Insert(TestEvent);
    // Throw error
    assertThrows(DataAccessException.class, () -> eventDAO.Insert(TestEvent));
  }

  @Test
  public void FindEventPass() throws DataAccessException {
    Event testEventOne = new Event("1001", "Nobibi", "ww", "China", "Shanghai", "eat", 1, 2, 1996);

    eventDAO.Insert(testEventOne);
    // find event by number if match pass
    Event findEvent = eventDAO.Find("1001");
    assertEquals(findEvent, testEventOne);
  }

  @Test
  public void FindEventFail() throws DataAccessException, SQLException {

    Event testEventOne = new Event("1001", "Nobibi", "ww", "China", "Shanghai", "eat", 1, 2, 1996);
    eventDAO.Insert(testEventOne);
    connection.commit();
    assertNull(eventDAO.Find("102"));
  }

  @Test
  public void FindAllEventsPass() throws DataAccessException, SQLException {
    ArrayList<Event> events = new ArrayList<Event>();
    ArrayList<Event> findEvents;

    Event testEventOne = new Event("1", "nobibi", "1", "Russia", "a", "eat", 1, 2, 1111);
    Event testEventTwo = new Event("1-2", "nobibi", "12", "China", "b", "eat", 12, 23, 2222);
    Event testEventThree = new Event("1-2-3", "nobibi", "123", "China", "c", "eat", 123, 234, 3333);
    Event testEventFive = new Event("1-2-3-4-5", "nobibi", "12345", "China", "d", "eat", 12345, 23456, 5555);
    //insert
    eventDAO.Insert(testEventOne);
    eventDAO.Insert(testEventTwo);
    eventDAO.Insert(testEventThree);
    eventDAO.Insert(testEventFive);
    connection.commit();

    // add
    events.add(testEventOne);
    events.add(testEventTwo);
    events.add(testEventThree);
    events.add(testEventFive);

    try {
      clearService.ClearDatabase();
      eventDAO.Insert(testEventOne);
      eventDAO.Insert(testEventTwo);
      eventDAO.Insert(testEventThree);
      eventDAO.Insert(testEventFive);

      findEvents = eventDAO.FindAll("nobibi");
        // loop throught how many times
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

    Event testEventOne = new Event("1", "nobibi", "1", "Russia", "a", "eat", 1, 2, 1111);
    Event testEventTwo = new Event("1-2", "nobibi", "12", "China", "b", "eat", 12, 23, 2222);
    Event testEventThree = new Event("1-2-3", "nobibi", "123", "China", "c", "eat", 123, 234, 3333);
    Event testEventFive = new Event("1-2-3-4-5", "asd", "12345", "China", "d", "eat", 12345, 23456, 5555);
    eventDAO.Insert(testEventOne);
    eventDAO.Insert(testEventTwo);
    eventDAO.Insert(testEventThree);
    eventDAO.Insert(testEventFive);
    connection.commit();


    events.add(testEventOne);
    events.add(testEventTwo);
    events.add(testEventThree);
    events.add(testEventFive);

    try {
      clearService.ClearDatabase();
      eventDAO.Insert(testEventOne);
      eventDAO.Insert(testEventTwo);
      eventDAO.Insert(testEventThree);
      eventDAO.Insert(testEventFive);

      findEvents = eventDAO.FindAll("nobibi");
      // it should only be three
      for (int i = 0; i < 3; i++) {
        if (events.get(i) == testEventFive) {
          assertNotEquals(events.get(i), findEvents.get(i));
        }
        else
        {
          assertEquals(events.get(i), findEvents.get(i));
        }
      }
    } catch (DataAccessException e)
    {
      e.printStackTrace();
    }
  }
}
