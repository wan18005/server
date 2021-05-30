package Results;

import Models.*;

import java.util.ArrayList;

/**
 * A class that returns a response based on the success of the operation provided by the service.
 */
public class PersonResult {

  private ArrayList<Person> data;
  private String personID;
  private String associatedUsername;
  private String firstName;
  private String lastName;
  private String gender;
  private String fatherID;
  private String motherID;
  private String spouseID;
  private String message;
  private boolean success;

  public PersonResult(String setMessage, boolean setBoolean) {
    this.setMessage(setMessage);
    this.setSuccess(setBoolean);
  }

  public PersonResult(Person p) {
    this.setPersonID(p.getPersonID());
    this.setAssociatedUsername(p.getAssociatedUsername());
    this.setFirstName(p.getFirstName());
    this.setLastName(p.getLastName());
    this.setGender(p.getGender());
    this.setFatherID(p.getFatherID());
    this.setMotherID(p.getMotherID());
    this.setSpouseID(p.getSpouseID());
    this.setSuccess(true);
  }

  public PersonResult(ArrayList<Person> data, Person p) {
    this.setPersonID(p.getPersonID());
    this.setAssociatedUsername(p.getAssociatedUsername());
    this.setFirstName(p.getFirstName());
    this.setLastName(p.getLastName());
    this.setGender(p.getGender());
    this.setFatherID(p.getFatherID());
    this.setMotherID(p.getMotherID());
    this.setSpouseID(p.getSpouseID());
    this.setData(data);
    this.setMessage(null);
    this.setSuccess(true);
  }

  public ArrayList<Person> getData() { return data; }
  public String getPersonID() { return personID; }
  public String getAssociatedUsername() { return associatedUsername; }
  public String getFirstName() { return firstName; }
  public String getLastName() { return lastName; }
  public String getGender() { return gender; }
  public String getFatherID() { return fatherID; }
  public String getSpouseID() { return spouseID; }
  public String getMessage() { return message; }
  public boolean getSuccess() { return success; }

  public void setData(ArrayList<Person> setPersons) { this.data = setPersons; }
  public void setPersonID(String setPersonID) { this.personID = setPersonID; }
  public void setAssociatedUsername(String setAssociatedUsername) { this.associatedUsername = setAssociatedUsername; }
  public void setFirstName(String setFirstName) { this.firstName = setFirstName; }
  public void setLastName(String setLastName) { this.lastName = setLastName; }
  public void setGender(String setGender) { this.gender = setGender; }
  public void setFatherID(String setFatherID) { this.fatherID = setFatherID; }
  public void setMotherID(String setMotherID) { this.motherID = setMotherID; }
  public void setSpouseID(String setSpouseID) { this.spouseID = setSpouseID; }
  public void setMessage(String setMessage) { this.message = setMessage; }
  public void setSuccess(boolean setSuccess) { this.success = setSuccess; }
}
