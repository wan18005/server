package Generators;

import java.util.ArrayList;

import Models.Person;
import Models.Event;

public class GenerationStorage {

  private ArrayList<Person> persons = null;
  private ArrayList<Event> events = null;

  public GenerationStorage(ArrayList<Person> setPersons, ArrayList<Event> setEvents) {
    this.persons = setPersons;
    this.events = setEvents;
  }

  public ArrayList<Person> getPersonsArray() { return persons; }

  public ArrayList<Event> getEventsArray() { return events; }
}
