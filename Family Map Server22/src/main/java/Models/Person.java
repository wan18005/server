package Models;

import java.util.Objects;
import java.util.UUID;

/**
 * The person class that represents a person in the data base. This class contains information like the
 * username of the person this person is associated with, the first name, last name, gender, and family ID tags
 * that the person has.
 */
public class Person {

  private String personID;
  private String associatedUsername;
  private String firstName;
  private String lastName;
  private String gender;
  private String fatherID;
  private String motherID;
  private String spouseID;

  /**
   * The default constructor
   */
  public Person() {
    personID = UUID.randomUUID().toString();
    associatedUsername = null;
    firstName = null;
    lastName = null;
    gender = null;
    fatherID = null;
    motherID = null;
    spouseID = null;
  }


  public Person(String setPersonID, String setAssociatedUsername, String setFirstName, String setLastName, String setGender,
                String setFatherID, String setMotherID, String setSpouseID) {
    this.personID = setPersonID;
    this.associatedUsername = setAssociatedUsername;
    this.firstName = setFirstName;
    this.lastName = setLastName;
    this.gender = setGender;
    this.fatherID = setFatherID;
    this.motherID = setMotherID;
    this.spouseID = setSpouseID;
  }

  public String getPersonID() { return personID; }
  public String getAssociatedUsername() { return associatedUsername; }
  public String getFirstName() { return firstName; }
  public String getLastName() { return lastName; }
  public String getGender() { return gender; }
  public String getFatherID() { return fatherID; }
  public String getMotherID() { return motherID; }
  public String getSpouseID() { return spouseID;}

  public void setPersonID(String personID) { this.personID = personID; }
  public void setAssociatedUsername(String associatedUsername) { this.associatedUsername = associatedUsername; }
  public void setFirstName(String firstName) { this.firstName = firstName; }
  public void setLastName(String lastName) { this.lastName = lastName; }
  public void setGender(String gender) { this.gender = gender; }
  public void setFatherID(String fatherID) { this.fatherID = fatherID; }
  public void setMotherID(String motherID) { this.motherID = motherID; }
  public void setSpouseID(String spouseID) { this.spouseID = spouseID; }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Person that = (Person) o;
    return personID.equals(that.personID) && associatedUsername.equals(that.associatedUsername) && firstName.equals(that.firstName) && lastName.equals(that.lastName) && gender.equals(that.gender) && Objects.equals(fatherID, that.fatherID) && Objects.equals(motherID, that.motherID) && Objects.equals(spouseID, that.spouseID);
  }
}
