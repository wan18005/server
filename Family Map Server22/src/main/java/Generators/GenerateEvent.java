package Generators;

import Models.Event;
import Models.Person;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.*;

public class GenerateEvent {

  GenerateLocations generatedLocation = new GenerateLocations();
  Random r = new Random();
  String username = null;
  ArrayList<Event> events = new ArrayList<Event>();

  public String EventType() {
    try {
      Random r = new Random();
      FileReader fr = new FileReader(new File("json/enames.json"));
      JsonParser jp = new JsonParser();
      JsonObject jo = (JsonObject) jp.parse(fr);
      JsonArray ja = (JsonArray) jo.get("data");

      int i = r.nextInt(ja.size());
      String event = ja.get(i).toString();
      event = event.substring(1, event.length() - 1);
      return event;

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void Marriage(Person husband, Person wife, int year) {
    int yearMarried = year + r.nextInt(6) + 22;

    Event husbandMarriage = generatedLocation.GenerateLocation();
    husbandMarriage.setPersonID(husband.getPersonID());
    husbandMarriage.setAssociatedUsername(username);
    husbandMarriage.setEventType("Marriage");
    husbandMarriage.setYear(yearMarried);

    Event wifeMarriage = JoinMarriage(husbandMarriage, wife, yearMarried);

    events.add(husbandMarriage);
    events.add(wifeMarriage);
  }

  public void RootBirth(Person rootPerson, int year) {
    Event birth = generatedLocation.GenerateLocation();

    birth.setPersonID(rootPerson.getPersonID());
    birth.setEventType("Birth");
    birth.setYear(year);
    birth.setAssociatedUsername(username);

    events.add(birth);
  }

  public void Birth(Person person, int year) {
    Event birth = generatedLocation.GenerateLocation();
    int yearBorn = year - r.nextInt(10);

    birth.setPersonID(person.getPersonID());
    birth.setEventType("Birth");
    birth.setYear(yearBorn);
    birth.setAssociatedUsername(username);

    events.add(birth);
  }

  public void Death(Person person, int year) {
    Event death = generatedLocation.GenerateLocation();
    int avgLifespan = 30;
    int yearDeath = year + avgLifespan + r.nextInt(50);
    if ((yearDeath - year) > 120) {
      yearDeath = year + 120;
    }

    death.setPersonID(person.getPersonID());
    death.setEventType("Death");
    death.setYear(yearDeath);
    death.setAssociatedUsername(username);

    events.add(death);
  }

  public void Random(Person person, int year) {
    int yearsBeforeBirth = 10;
    int eventYear = year + yearsBeforeBirth + r.nextInt(20);

    Event random = generatedLocation.GenerateLocation();
    random.setPersonID(person.getPersonID());
    random.setEventType(EventType());
    random.setYear(eventYear);
    random.setAssociatedUsername(username);

    events.add(random);
  }

  private Event JoinMarriage(Event husbandMarriage, Person wife, int yearMarried) {
    Event wifeMarriage = new Event();

    wifeMarriage.setPersonID(wife.getPersonID());
    wifeMarriage.setAssociatedUsername(username);
    wifeMarriage.setEventType("Marriage");
    wifeMarriage.setYear(yearMarried);
    wifeMarriage.setLongitude(husbandMarriage.getLongitude());
    wifeMarriage.setLatitude(husbandMarriage.getLatitude());
    wifeMarriage.setCity(husbandMarriage.getCity());
    wifeMarriage.setCountry(husbandMarriage.getCountry());

    return wifeMarriage;
  }


  public GenerateEvent(String setUsername) { username = setUsername; }

  public ArrayList<Event> GetEvents() { return events; }


}
