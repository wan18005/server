package FakeDataAlgo;

import java.util.*;
import Models.Person;
import Models.Event;

/**
 * Temp storage
 */

public class FakeTemp
{
  private ArrayList<Person> personArrayList;
  private ArrayList<Event> eventArrayList;

  public ArrayList<Person> getPersonArrayList() { return personArrayList;}
  public ArrayList<Event> getEventArrayList() { return eventArrayList; }


  public FakeTemp (ArrayList<Person> setPerson, ArrayList<Event> setEvent)
  {
    this.personArrayList = setPerson;
    this.eventArrayList = setEvent;
  }
}
