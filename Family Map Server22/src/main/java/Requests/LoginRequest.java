package Requests;
import java.io.*;
import java.util.*;
/**
 * turn gson request to Java login object.
 */
public class LoginRequest {

  private String username;
  private String password;

  // getter and setter for username done by code
  public String getUsername() {
    return username;
  }
  public void setUsername(String setUsername) {
    this.username = setUsername;
  }


  // getter and setter for password done by code
  public String getPassword() { return password; }
  public void setPassword(String setPassword) {
    this.password = setPassword;
  }

  //default
  public LoginRequest() {
    username = null;
    password = null;
  }
  //constructor
  public LoginRequest(String setUsername, String setPassword) {
    this.setUsername(setUsername);
    this.setPassword(setPassword);
  }

}
