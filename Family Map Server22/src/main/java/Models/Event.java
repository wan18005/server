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
  public void setEventID(String EventID) { this.eventID = EventID; }
  public void setAssociatedUsername(String AssociatedUsername) { this.associatedUsername = AssociatedUsername; }
  public void setPersonID(String PersonID) { this.personID = PersonID; }
  public void setCountry(String Country) { this.country = Country; }
  public void setCity(String City) { this.city = City; }
  public void setEventType(String EventType) { this.eventType = EventType; }
  public void setLatitude(Float Latitude) { this.latitude = Latitude; }
  public void setLongitude(Float Longitude) { this.longitude = Longitude; }
  public void setYear(Integer Year) { this.year = Year; }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Event event = (Event) o;
    return Float.compare(event.latitude, latitude) == 0 && Float.compare(event.longitude, longitude) == 0 && year == event.year && eventID.equals(event.eventID) && associatedUsername.equals(event.associatedUsername) && personID.equals(event.personID) && country.equals(event.country) && city.equals(event.city) && eventType.equals(event.eventType);
  }
}

