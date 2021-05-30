package DataAccessObjects;

import java.sql.*;

/**
 * A class DAO is a class that access the whole database
 */
public class DAO {
  //A connection (session) with a specific database.
  // SQL statements are executed and results are returned within the context of a connection.
  public Connection connection;

  //Whenever we want to make a change to our database we will have to open a connection and use
  //Statements created by that connection to initiate transactions
  public Connection openConnection() throws DataAccessException {
    try {
      //The Structure for this Connection is driver:language:path
      //The path assumes you start in the root of your project unless given a non-relative path
      final String CONNECTION_URL = "jdbc:sqlite:database.db";

      // Open a database connection to the file given in the path
      connection = DriverManager.getConnection(CONNECTION_URL);

      // Start a transaction
      connection.setAutoCommit(false);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Unable to open connection to database");
    }

    return connection;
  }


  public Connection getConnection() throws DataAccessException {
    if (connection == null) {
      return openConnection();
    } else {
      return connection;
    }
  }

  //When we are done manipulating the database it is important to close the connection. This will
  //End the transaction and allow us to either commit our changes to the database or rollback any
  //changes that were made before we encountered a potential error.


  //IMPORTANT: IF YOU FAIL TO CLOSE A CONNECTION AND TRY TO REOPEN THE DATABASE THIS WILL CAUSE THE
  //DATABASE TO LOCK. YOUR CODE MUST ALWAYS INCLUDE A CLOSURE OF THE DATABASE NO MATTER WHAT ERRORS
  //OR PROBLEMS YOU ENCOUNTER
  public void closeConnection(boolean commit) throws DataAccessException {
    try {
      if (commit) {
        //This will commit the changes to the database
        connection.commit();
      } else {
        //If we find out something went wrong, pass a false into closeConnection and this
        //will rollback any changes we made during this connection
        connection.rollback();
      }

      connection.close();
      connection = null;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new DataAccessException("Unable to close database connection");
    }
  }

  /**
   * This will clear all of the tables in the database.
   */
  public void ClearTables() throws DataAccessException {

    try {
      ClearHelper();
    } catch (DataAccessException e) {
      System.out.println(e.toString());
    }
  }

  /**
   * This will delete all of the tables in the database.
   */
  private void ClearHelper() throws DataAccessException {
    PreparedStatement stmt = null;

    try {
      String sqlUsers = "DROP TABLE IF EXISTS Users; \n";
      String sqlPersons = "DROP TABLE IF EXISTS Persons; \n";
      String sqlEvents = "DROP TABLE IF EXISTS Events; \n";
      String sqlAuthToken = "DROP TABLE IF EXISTS AuthToken; \n";

      stmt = connection.prepareStatement(sqlUsers);
      stmt.executeUpdate();
      stmt = connection.prepareStatement(sqlPersons);
      stmt.executeUpdate();
      stmt = connection.prepareStatement(sqlEvents);
      stmt.executeUpdate();
      stmt = connection.prepareStatement(sqlAuthToken);
      stmt.executeUpdate();
      try {
        CreateTables();
      } catch (DataAccessException e) {
        System.out.println(e.toString());
      }

    } catch (SQLException e) {
      System.out.println(e.toString());
      throw new DataAccessException(e.toString());
    }
  }

  /**
   * This will create all of the tables for the database.
   */
  public void CreateTables() throws DataAccessException, SQLException {
    PreparedStatement stmt = null;
    try {
      String sqlUsers = "CREATE TABLE IF NOT EXISTS `Users` (\n" +
              "\t`Username`\ttext NOT NULL,\n" +
              "\t`Password`\ttext NOT NULL,\n" +
              "\t`Email`\ttext NOT NULL,\n" +
              "\t`First_Name`\ttext NOT NULL,\n" +
              "\t`Last_Name`\ttext NOT NULL,\n" +
              "\t`Gender`\ttext NOT NULL,\n" +
              "\t`Person_ID`\ttext NOT NULL,\n" +
              "\tPRIMARY KEY(`Username`)\n" +
              ");\n";

      String sqlPersons = "CREATE TABLE IF NOT EXISTS `Persons` (\n" +
              "\t`Person_ID`\ttext NOT NULL,\n" +
              "\t`Username`\ttext NOT NULL,\n" +
              "\t`First_Name`\ttext NOT NULL,\n" +
              "\t`Last_Name`\ttext NOT NULL,\n" +
              "\t`Gender`\ttext NOT NULL,\n" +
              "\t`Father_ID`\ttext ,\n" +
              "\t`Mother_ID`\ttext ,\n" +
              "\t`Spouse_ID`\ttext,\n" +
              "\tPRIMARY KEY(`Person_ID`)\n" +
              ");\n";

      String sqlEvents = "CREATE TABLE IF NOT EXISTS `Events` (\n" +
              "\t`EventID`\ttext NOT NULL UNIQUE,\n" +
              "\t`AssociatedUsername`\ttext NOT NULL,\n" +
              "\t`PersonID`\ttext NOT NULL,\n" +
              "\t`Country`\ttext NOT NULL,\n" +
              "\t`City`\ttext NOT NULL,\n" +
              "\t`EventType`\ttext NOT NULL,\n" +
              "\t`Latitude`\treal NOT NULL,\n" +
              "\t`Longitude`\treal NOT NULL,\n" +
              "\t`Year`\tint NOT NULL,\n" +
              "\tPRIMARY KEY(`EventID`),\n" +
              "\tFOREIGN KEY('AssociatedUsername') references 'Users'('Username'),\n" +
              "\tFOREIGN KEY('PersonID') references 'Persons'('Person_ID')\n" +
              ");\n";

      String sqlAuthToken = "CREATE TABLE IF NOT EXISTS `AuthToken` (\n" +
              "\t`Username`\ttext NOT NULL,\n" +
              "\t`Auth_Token`\ttext NOT NULL UNIQUE,\n" +
              "\tPRIMARY KEY(`Auth_Token`)\n" +
              ");\n";

      stmt = connection.prepareStatement(sqlUsers);
      stmt.executeUpdate();
      stmt = connection.prepareStatement(sqlPersons);
      stmt.executeUpdate();
      stmt = connection.prepareStatement(sqlEvents);
      stmt.executeUpdate();
      stmt = connection.prepareStatement(sqlAuthToken);
      stmt.executeUpdate();
      stmt.close();
    } catch (SQLException e) {
      throw new DataAccessException(e.toString());
    }
  }
}
