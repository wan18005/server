package Service.Services;

import DataAccessObjects.*;
import Generators.GenerateData;
import Generators.GenerationStorage;
import Models.Event;
import Models.Person;
import Models.User;
import Results.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class that will handle Fill business logic and determine if a request was successful.
 */
public class FillService {
  private EventsDAO eventsDAO;
  private PersonsDAO personsDAO;
  private UsersDAO usersDAO;
  private DAO db;
  private Connection conn;

  public FillResult Fill(String username, int numGen) throws DataAccessException {
    GenerateData generateData = new GenerateData();
    db = new DAO();
    conn = db.getConnection();
    eventsDAO = new EventsDAO(conn);
    personsDAO = new PersonsDAO(conn);
    usersDAO = new UsersDAO(conn);

    if (numGen <= 0) {
      db.closeConnection(false);
      return new FillResult("Error: Number of generations is less than or equal to 0.", false);
    }

    try {
      if (usersDAO.Find(username) == null) {
        db.closeConnection(false);
        return new FillResult("Error: User does not exist.", false);
      } else if (! ClearUsersInfo(username)) {
        db.closeConnection(false);
        return new FillResult("Error: Failed to delete " + username + " Events and Persons" +
                " information from the database.", false);
      } else {
        Person temp = UserToPerson(usersDAO.Find(username));
        GenerationStorage generationStorage = generateData.PopulateGenerations(temp, numGen);
        Insert(generationStorage.getPersonsArray(), generationStorage.getEventsArray());
        ;
        db.closeConnection(true);
        return new FillResult("Successfully added " + generationStorage.getPersonsArray().size() +
                " persons and " + generationStorage.getEventsArray().size() + " events.", true);
      }
    } catch (DataAccessException e) {
      e.printStackTrace();
      db.closeConnection(false);
    }
    db.closeConnection(false);
    return new FillResult("Error: Fatal fill error.", false);
  }

  private boolean ClearUsersInfo(String username) {
    boolean success = false;

    if (eventsDAO.Delete(username) && personsDAO.Delete(username)) {
      try {
        conn.commit();
      } catch (SQLException throwables) {
        throwables.printStackTrace();
      }
      success = true;
    }
    return success;
  }

  private Person UserToPerson(User user) {
    Person p = new Person();

    p.setPersonID(user.getPersonID());
    p.setAssociatedUsername(user.getUsername());
    p.setFirstName(user.getFirstName());
    p.setLastName(user.getLastName());
    p.setGender(user.getGender());

    return p;
  }

  private void Insert(ArrayList<Person> persons, ArrayList<Event> events) throws DataAccessException {
    if (persons.size() == 0) {
      throw new DataAccessException("Error: Persons array is empty.");
    }
    if (events.size() == 0) {
      throw new DataAccessException("Error: Events array is empty.");
    }
    for (Person temp : persons) {
      personsDAO.Insert(temp);
    }
    for (Event temp : events) {
      eventsDAO.Insert(temp);
    }
  }
}
