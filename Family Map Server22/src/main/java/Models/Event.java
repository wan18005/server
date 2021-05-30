package Models;

import java.util.UUID;

/**
 * Event Model class
 */
public class Event {
  //variables
  private String eventID;
  private String associatedUsername;
  private String personID;
  private String country;
  private String city;
  private String eventType;
  private float latitude;
  private float longitude;
  private int year;
  // default
  public Event()
  {
    eventID = UUID.randomUUID().toString();
    associatedUsername = null;
    personID = null;
    country = null;
    city = null;
    eventType = null;
    latitude = 0.0f;
    longitude = 0.0f;
    year = 0;
  }


  public Event(String setEventID, String setAssociatedUsername, String setPersonID, String setCountry, String setCity, String setEventType,
               float setLatitude, float setLongitude, int setYear) {
    this.setEventID(setEventID);
    this.setAssociatedUsername(setAssociatedUsername);
    this.setPersonID(setPersonID);
    this.setCountry(setCountry);
    this.setCity(setCity);
    this.setEventType(setEventType);
    this.setLatitude(setLatitude);
    this.setLongitude(setLongitude);
    this.setYear(setYear);
  }

  public Event(String setCity, String setCountry, float setLatitude, float setLongitude) {
    this.eventID = UUID.randomUUID().toString();
    this.setCity(setCity);
    this.setCountry(setCountry);
    this.setLatitude(setLatitude);
    this.setLongitude(setLongitude);
  }

  //getter
  public String getEventID() { return eventID; }
  public String getAssociatedUsername() { return associatedUsername; }
  public String getPersonID() { return personID; }
  public String getCountry() { return country; }
  public String getCity() { return city; }
  public String getEventType() { return eventType; }
  public float getLatitude() { return latitude; }
  public float getLongitude() { return longitude; }
  public int getYear() { return year; }
  //setter
  public void setEventID(String setEventID) { this.eventID = setEventID; }
  public void setAssociatedUsername(String setAssociatedUsername) { this.associatedUsername = setAssociatedUsername; }
  public void setPersonID(String setPersonID) { this.personID = setPersonID; }
  public void setCountry(String setCountry) { this.country = setCountry; }
  public void setCity(String setCity) { this.city = setCity; }
  public void setEventType(String setEventType) { this.eventType = setEventType; }
  public void setLatitude(Float setLatitude) { this.latitude = setLatitude; }
  public void setLongitude(Float setLongitude) { this.longitude = setLongitude; }
  public void setYear(Integer setYear) { this.year = setYear; }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Event event = (Event) o;
    return Float.compare(event.latitude, latitude) == 0 && Float.compare(event.longitude, longitude) == 0 && year == event.year && eventID.equals(event.eventID) && associatedUsername.equals(event.associatedUsername) && personID.equals(event.personID) && country.equals(event.country) && city.equals(event.city) && eventType.equals(event.eventType);
  }
}

