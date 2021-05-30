package Results;

import Models.Event;

import java.util.ArrayList;

/**
 * A class that returns a response based on the success of the operation provided by the service.
 */
public class EventResult {

  private ArrayList<Event> data;
  private String eventID;
  private String associatedUsername;
  private String personID;
  private String country;
  private String city;
  private String eventType;
  private Float latitude;
  private Float longitude;
  private Integer year;
  private String message;
  private boolean success;

  public EventResult(String setMessage, boolean setSuccess) {
    this.setMessage(setMessage);
    this.setSuccess(setSuccess);
  }

  public EventResult(ArrayList<Event> setEvents) {
    this.setData(setEvents);
    this.setSuccess(true);
  }

  public EventResult(Event event, String username) {
    this.setEventID(event.getEventID());
    this.setAssociatedUsername(username);
    this.setPersonID(event.getPersonID());
    this.setCountry(event.getCountry());
    this.setCity(event.getCity());
    this.setEventType(event.getEventType());
    this.setLatitude(event.getLatitude());
    this.setLongitude(event.getLongitude());
    this.setYear(event.getYear());
    this.setSuccess(true);
  }

  public ArrayList<Event> getData() { return data; }
  public String getEventID() { return eventID; }
  public String getAssociatedUsername() { return associatedUsername; }
  public String getPersonID() { return personID; }
  public String getCountry() { return country; }
  public String getCity() { return city; }
  public String getEventType() { return eventType; }
  public Float getLatitude() { return latitude; }
  public Float getLongitude() { return longitude; }
  public Integer getYear() { return year; }
  public String getMessage() { return message; }
  public boolean getSuccess() { return success; }

  public void setData(ArrayList<Event> setEvents) { this.data = setEvents; }
  public void setEventID(String setEventID) { this.eventID = setEventID; }
  public void setAssociatedUsername(String setAssociatedUsername) { this.associatedUsername = setAssociatedUsername; }
  public void setPersonID(String setPersonID) { this.personID = setPersonID; }
  public void setCountry(String setCountry) { this.country = setCountry; }
  public void setCity(String setCity) { this.city = setCity; }
  public void setEventType(String setEventType) { this.eventType = setEventType; }
  public void setLatitude(Float setLatitude) { this.latitude = setLatitude; }
  public void setLongitude(Float setLongitude) { this.longitude = setLongitude; }
  public void setYear(Integer setYear) { this.year = setYear; }
  public void setMessage(String setMessage) { this.message = setMessage; }
  public void setSuccess(boolean setSuccess) { this.success = setSuccess; }
}



