package Services;

import DataAccessObjects.*;
import FakeDataAlgo.FakeData;
import FakeDataAlgo.FakeTemp;
import Models.AuthToken;
import Models.Event;
import Models.Person;
import Models.User;
import Results.*;
import Requests.*;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * Class that will handle Register business logic and determine if a request was successful.
 */
public class RegisterService {

  private AuthTokenDAO authTokenDAO;
  private EventsDAO eventDAO;
  private PersonsDAO personDAO;
  private UsersDAO userDAO;

  private AuthToken authToken = new AuthToken();
  private Event event = new Event();
  private Person person;
  private User user;
  private FakeData fakeData = new FakeData();

  private int defaultGens = 4;

  private boolean ValidInput(RegisterRequest r)
  {
    if ((r.getUsername() == null) ||
            (r.getPassword() == null) ||
            (r.getEmail() == null) ||
            (r.getFirstName() == null) ||
            (r.getLastName() == null) ||
            (r.getGender() == null))
      return false;
    else
      return  true;
  }

  private void CreateUser(RegisterRequest r) {
    user = new User();
    user.setUsername(r.getUsername());
    user.setPassword(r.getPassword());
    user.setEmail(r.getEmail());
    user.setFirstName(r.getFirstName());
    user.setLastName(r.getLastName());
    user.setGender(r.getGender());
    user.setPersonID(person.getPersonID());
  }

  private void CreatePerson(RegisterRequest r) {
    person = new Person();
    person.setAssociatedUsername(r.getUsername());
    person.setFirstName(r.getFirstName());
    person.setLastName(r.getLastName());
    person.setGender(r.getGender());
  }

  private void Insert(ArrayList<Person> personArrayList, ArrayList<Event> eventArrayList) throws DataAccessException
  {
    if (personArrayList.size() == 0)
    {
      throw new DataAccessException("Error: Persons array is empty.");
    }
    if (eventArrayList.size() == 0) {
      throw new DataAccessException("Error: Events array is empty.");
    }
    for (Person temp : personArrayList) {
      personDAO.Insert(temp);
    }
    for (Event temp : eventArrayList) {
      eventDAO.Insert(temp);
    }
  }

  public RegisterResult Register(RegisterRequest request) throws DataAccessException {
    DAO db = new DAO();
    Connection conn = db.getConnection();
    authTokenDAO = new AuthTokenDAO(conn);
    eventDAO = new EventsDAO(conn);
    personDAO = new PersonsDAO(conn);
    userDAO = new UsersDAO(conn);

    if (! ValidInput(request))
    {
      return new RegisterResult("Error: Invalid input.", false);
    }

    CreatePerson(request);
    CreateUser(request);

    try
    {
      if (userDAO.Find(user.getUsername()) == null)
      {
        userDAO.Insert(user);
        authToken = new AuthToken(user.getUsername());
        authTokenDAO.Insert(authToken);
        FakeTemp fakeTemp = fakeData.Generatrion(person, defaultGens);
        Insert(fakeTemp.getPersonArrayList(), fakeTemp.getEventArrayList());
        db.closeConnection(true);
        return new RegisterResult(authToken.getAuthtoken(), user.getUsername(), person.getPersonID());
      }
      else
      {
        db.closeConnection(false);
        return new RegisterResult("Error: Username is already taken by another user.", false);
      }
    }
    catch (DataAccessException e)
    {
      e.printStackTrace();
      db.closeConnection(false);
      return new RegisterResult(e.toString(), false);
    }

  }
}
