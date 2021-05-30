package Requests;

import Models.*;

/**
 * A class that packages a gson request and turns it into a Java object.
 */
public class LoadRequest {

  private User[] users;
  private Person[] persons;
  private Event[] events;

  public LoadRequest() {
    this.users = null;
    this.persons = null;
    this.events = null;
  }

  public LoadRequest(User[] setUserArray, Person[] setPersonArray, Event[] setEventArray) {
    this.users = setUserArray;
    this.persons = setPersonArray;
    this.events = setEventArray;
  }

  public User[] getUsers() { return users; }
  public Person[] getPersons() { return persons; }
  public Event[] getEvents() { return events; }

  public void setUsers(User[] users) { this.users = users; }
  public void setPersons(Person[] persons) { this.persons = persons; }
  public void setEvents(Event[] events) { this.events = events; }
}
