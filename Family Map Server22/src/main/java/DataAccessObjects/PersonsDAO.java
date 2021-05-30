package DataAccessObjects;

import Models.*;

import java.sql.*;
import java.util.*;

/**
 * A class used for accessing the Person table in the database.
 */
public class PersonsDAO {
  private Connection conn;

  public PersonsDAO(Connection conn) {this.conn = conn; }



  /**
   * Inserts the event into the Event table in the database.
   *
   * @param person
   * @return true or error message
   */

  /*
Person ID: Unique identifier for this person (non-empty string)
Associated Username: User (Username) to which this person belongs
First Name: Person’s first name (non-empty string)
Last Name: Person’s last name (non-empty string)
Gender: Person’s gender (string: “f” or “m”)
Father ID: Person ID of person’s father (possibly null)
Mother ID: Person ID of person’s mother (possibly null)
Spouse ID: Person ID of person’s spouse (possibly null)
   */
  public void Insert(Person person) throws DataAccessException {
    String sql = "INSERT INTO Persons (Person_ID, Username, First_Name, Last_Name, Gender, Father_ID, Mother_ID, Spouse_ID)" +
            " VALUES(?,?,?,?,?,?,?,?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, person.getPersonID());
      stmt.setString(2, person.getAssociatedUsername());
      stmt.setString(3, person.getFirstName());
      stmt.setString(4, person.getLastName());
      stmt.setString(5, person.getGender());
      stmt.setString(6, person.getFatherID());
      stmt.setString(7, person.getMotherID());
      stmt.setString(8, person.getSpouseID());
      // update
      stmt.executeUpdate();
    } catch (SQLException e)
    {
      throw new DataAccessException("Error while inserting into person table in the database");
    }
  }
  /**
   * Delete the person table by the given AssociatedUserName
   * @return true or false.
   */
  public Boolean Delete(String username) {
    String sql = "DELETE FROM Persons WHERE Username = ?;";

    try (PreparedStatement PS = conn.prepareStatement(sql)) {
      PS.setString(1, username);
      PS.executeUpdate();

    } catch (SQLException e)
    {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * Clears the person table in the database.
   * @return true or false.
   */
  public boolean Clear() throws DataAccessException
  {
    boolean clearStatues = false;
    String sql = "DROP TABLE IF EXISTS Persons;";
    try (PreparedStatement PS = conn.prepareStatement(sql))
    {
      PS.executeUpdate();
      clearStatues = true;
    }
    catch (SQLException e) {
      e.printStackTrace();
      clearStatues = false;
      System.out.println("Error Clearing person's Table, return false\n");
    }
    return clearStatues;
  }
  /**
   * find the person table in the database with the ID given.
   * @return true or false.
   */
  public Person Find(String person_ID) throws DataAccessException {
    //Person person;
    ResultSet rs = null;
    String sql = "SELECT * FROM Persons WHERE Person_ID = ?;";
    try (PreparedStatement PS = conn.prepareStatement(sql)) {
      PS.setString(1, person_ID);
      rs = PS.executeQuery();
      if (rs.next()) {
          /*
      PS.setString(1, person.getPersonID());
      PS.setString(2, person.getAssociatedUserName());
      PS.setString(3, person.getFirstName());
      PS.setString(4, person.getLastName());
      PS.setString(5, person.getGender());
      PS.setString(6, person.getFatherID());
      PS.setString(7, person.getMotherID());
      PS.setString(8, person.getSpouseID());
           */
        String personID = rs.getString(1);
        String AssociatedUserName = rs.getString(2);
        String firstName = rs.getString(3);
        String lastName = rs.getString(4);
        String gender = rs.getString(5);
        String fatherID = rs.getString(6);
        String motherID = rs.getString(7);
        String spouseID = rs.getString(8);

        Person person = new Person(personID,AssociatedUserName,firstName,lastName,gender,fatherID,motherID,spouseID);
        return person;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding person");
    } finally {
      if (rs != null) {
        try {
          rs.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }
  /**
   * find the all the person with the given username in person table in the database.
   * @return Persons
   */
  public ArrayList<Person> FindAll(String username) throws DataAccessException
  {
    //using to store the event that is found
    ArrayList<Person> persons = new ArrayList<Person>();
    ResultSet resultSet = null;
    String sql = "SELECT * FROM Persons WHERE Username = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      resultSet = stmt.executeQuery();
      while (resultSet.next()) {
        String personID = resultSet.getString(1);
        String AssociatedUserName = resultSet.getString(2);
        String firstName = resultSet.getString(3);
        String lastName = resultSet.getString(4);
        String gender = resultSet.getString(5);
        String fatherID = resultSet.getString(6);
        String motherID = resultSet.getString(7);
        String spouseID = resultSet.getString(8);
        Person person = new Person(personID,AssociatedUserName,firstName,lastName,gender,fatherID,motherID,spouseID);
        persons.add(person);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error when finding all persons associated with " + username);
    }
    finally
    {
      if (resultSet != null)
      {
        try
        {
          resultSet.close();
        }
        catch (SQLException e)
        {
          e.printStackTrace();
        }
      }
    }
    if (persons.size() == 0)
    {
      return null;
    } else
    {
      return persons;
    }
  }

  public void setPersonID(String setPersonID, Person person) { person.setPersonID(setPersonID);}
  public void setFirstName(String setFirstName, Person person) { person.setFirstName(setFirstName); }
  public void setLastName(String setLastName, Person person) { person.setLastName(setLastName); }
  public void setGender(String setGender, Person person) { person.setGender(setGender); }
  public void setAssociatedUsername(String setAssociatedUsername, Person person) { person.setAssociatedUsername(setAssociatedUsername); }
}
