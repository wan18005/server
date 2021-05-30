package UnitTests;

import DataAccessObjects.DataAccessException;

import Models.*;

import Requests.LoadRequest;
import Results.LoadResult;
import Services.ClearService;
import Services.LoadService;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoadServiceTest {

  @Test
  public void LoadPass() throws DataAccessException {
    ClearService clearService = new ClearService();

    Event eOne = new Event("123", "aa", "Alpha1", "China", "EE", "Dead", 100f, 900f, 6969);
    Event eTwo = new Event("123", "bb", "Alpha2", "Taiwan", "EE", "Shoppping", 100f, 900f, 9696);

    Person pOne = new Person("123", "aa", "hg", "ds", "F", "123abc", "abc123", null);
    Person pTwo = new Person("123", "bb", "fs", "fd", "F", null, null, null);

    User uOne = new User("aa", "1", "nobibi1@gmail.com", "Op", "gg", "T", "opggT");
    User uTwo = new User("bb", "killoptimus", "bumblebeestinks@yahoo.com", "Mega", "Tron", "R", "Me123");

    Event[] eventArray = new Event[]{eOne, eTwo};
    Person[] personArray = new Person[]{pOne, pTwo};
    User[] userArray = new User[]{uOne,uTwo};

    LoadRequest loadRequest = new LoadRequest(userArray, personArray, eventArray);
    LoadService loadService = new LoadService();
    LoadResult loadResult = loadService.Load(loadRequest);

    assertEquals("Successfully added 2 users, 2 persons, and 2 events.", loadResult.getMessage());

    clearService.ClearDatabase();
  }

  @Test
  public void LoadFail() throws DataAccessException {
    ClearService clearService = new ClearService();

    Event eOne = new Event("123", "aa", "Alpha1", "China", "EE", "Dead", 100f, 900f, 6969);
    Event eTwo = new Event("123", "bb", "Alpha2", "Taiwan", "EE", "Shoppping", 100f, 900f, 9696);
    Event eBad = new Event();
    Person pOne = new Person("123", "aa", "hg", "ds", "F", "123abc", "abc123", null);
    Person pTwo = new Person("123", "bb", "fs", "fd", "F", null, null, null);

    User uOne = new User("aa", "1", "nobibi1@gmail.com", "Op", "gg", "T", "opggT");
    User uTwo = new User("bb", "killoptimus", "bumblebeestinks@yahoo.com", "Mega", "Tron", "R", "Me123");

    Event[] events = new Event[]{eOne, eTwo,eBad};
    Person[] persons = new Person[]{pOne, pTwo};
    User[] users = new User[]{uOne, uTwo};

    LoadRequest loadRequest = new LoadRequest(users, persons, events);
    LoadService loadService = new LoadService();
    LoadResult loadResult = loadService.Load(loadRequest);

    assertEquals("Error: Invalid input.", loadResult.getMessage());

    clearService.ClearDatabase();
  }
}
