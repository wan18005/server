package DataAccessObjects;

import Models.*;
import java.sql.*;
/**
 * A class used for accessing the User table in the database.
 */
public class UsersDAO {

  private Connection conn;

  public UsersDAO(Connection conn) {this.conn = conn; }

  /**
   * Inserts the user into the User table in the database.
   *
   * @param user
   * @return true or error message
   */

  /*
  Username: Unique username (non-empty string)
  Password: User’s password (non-empty string)
  Email: User’s email address (non-empty string)
  First Name: User’s first name (non-empty string)
  Last Name: User’s last name (non-empty string)
  Gender: User’s gender (string: “f” or “m”)
  Person ID: Unique Person ID assigned to this user’s generated Person object - see Family History Information section for details (non-empty string)
 */
  public void Insert(User user) throws DataAccessException {
    String sql = "INSERT INTO Users (Username, Password, Email, First_Name, Last_Name, Gender, Person_ID)" +
            " VALUES(?,?,?,?,?,?,?)";
    try (PreparedStatement PS = conn.prepareStatement(sql)) {
      PS.setString(1, user.getUsername());
      PS.setString(2, user.getPassword());
      PS.setString(3, user.getEmail());
      PS.setString(4, user.getFirstName());
      PS.setString(5, user.getLastName());
      PS.setString(6, user.getGender());
      PS.setString(7, user.getPersonID());
      //update
      PS.executeUpdate();
    } catch (SQLException e) {
      throw new DataAccessException("Error while inserting into user table in the database");
    }
  }

  /**
   * Clears the User table in the database.
   * @return true or false.
   */
  public boolean Clear() throws DataAccessException {
    boolean status = false;
    PreparedStatement stmt = null;
    String sqlUsersDrop = "DROP TABLE IF EXISTS Users;";
    String sqlUsersCreate = "CREATE TABLE IF NOT EXISTS `Users` (\n" +
            "\t`Username`\ttext NOT NULL,\n" +
            "\t`Password`\ttext NOT NULL,\n" +
            "\t`Email`\ttext NOT NULL,\n" +
            "\t`First_Name`\ttext NOT NULL,\n" +
            "\t`Last_Name`\ttext NOT NULL,\n" +
            "\t`Gender`\ttext NOT NULL,\n" +
            "\t`Person_ID`\ttext NOT NULL,\n" +
            "\tPRIMARY KEY(`Username`)\n" +
            ");\n";
    try {
      stmt = conn.prepareStatement(sqlUsersDrop);
      stmt.executeUpdate();
      stmt = conn.prepareStatement(sqlUsersCreate);
      stmt.executeUpdate();
      status = true;
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Error Clearing User's Table\n");
    }
    return status;
  }
  /**
   * find the user table in the database with the username given.
   * @return true or false.
   */
  public User Find(String username) throws DataAccessException {
    //User user;
    ResultSet resultSet = null;
    String sql = "SELECT * FROM Users WHERE Username = ?;";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, username);
      resultSet = stmt.executeQuery();
      if (resultSet.next()) {
        String UserName = resultSet.getString(1);
        String PassWord = resultSet.getString(2);
        String Email_address = resultSet.getString(3);
        String firstName = resultSet.getString(4);
        String lastName = resultSet.getString(5);
        String gender = resultSet.getString(6);
        String personID = resultSet.getString(7);
        User user = new User(UserName,PassWord,Email_address,firstName,lastName,gender,personID);
        return user;
      }

    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Error encountered while finding user: " + username);
    } finally {
      if (resultSet != null) {
        try {
          resultSet.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }
}
