package Services;

import DataAccessObjects.*;
import FakeDataAlgo.FakeData;
import FakeDataAlgo.FakeTemp;
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
  private EventsDAO eventDAO;
  private PersonsDAO personDAO;
  private UsersDAO userDAO;
  private DAO dao;
  private Connection connection;


  private void Insert(ArrayList<Person> personArrayList, ArrayList<Event> eventArrayList) throws DataAccessException
  {
    if (personArrayList.size() == 0) {
      throw new DataAccessException("Error: Persons array is empty.");
    }
    if (eventArrayList.size() == 0) {
      throw new DataAccessException("Error: Events array is empty.");
    }
    //update
    for (Person temp : personArrayList)
    {
      personDAO.Insert(temp);
    }
    for (Event temp : eventArrayList)
    {
      eventDAO.Insert(temp);
    }
  }


  public FillResult Fill(String username, int numGen) throws DataAccessException {
    dao = new DAO();
    connection = dao.getConnection();
    eventDAO = new EventsDAO(connection);
    personDAO = new PersonsDAO(connection);
    userDAO = new UsersDAO(connection);
    FakeData fakeData = new FakeData();

    if (numGen <= 0)
    {
      dao.closeConnection(false);
      return new FillResult("Error: Number of generations is less than or equal to 0.", false);
    }

    try
    {
      if (userDAO.Find(username) == null)
      {
        dao.closeConnection(false);
        return new FillResult("Error: User does not exist.", false);
      }
      else if (! ClearUsersInfo(username))
      {
        dao.closeConnection(false);
        return new FillResult("Error: Failed to delete " + username + " Events and Persons" +
                " information from the database.", false);
      }
      else
      {
        Person temp = UserToPerson(userDAO.Find(username));
        FakeTemp fakeTemp = fakeData.Generatrion(temp, numGen);
        Insert(fakeTemp.getPersonArrayList(), fakeTemp.getEventArrayList());
        dao.closeConnection(true);
        return new FillResult("Successfully added " + fakeTemp.getPersonArrayList().size() +
                " persons and " + fakeTemp.getEventArrayList().size() + " events.", true);
      }
    }
    catch (DataAccessException e)
    {
      e.printStackTrace();
      dao.closeConnection(false);
    }
    dao.closeConnection(false);
    return new FillResult("Error: Fatal fill error.", false);
  }


  private boolean ClearUsersInfo(String username) {
    boolean status = false;

    if (eventDAO.Delete(username) && personDAO.Delete(username))
    {
      try
      {
        connection.commit();
      }
      catch (SQLException e) {
        e.printStackTrace();
      }
      status = true;
    }
    return status;
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


}

