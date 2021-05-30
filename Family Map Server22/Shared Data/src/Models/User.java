package Models;

/**
 * The user class is a model class that represents a user account using the username, password, email,
 * first name, last name, gender and unique person id.
 */
public class User {

  private String username;
  private String password;
  private String email;
  private String firstName;
  private String lastName;
  private String gender;
  private String personID;

  /**
   * This is the default constructor that will initialize all the local variables to null.
   */
  public User() {
    username = null;
    password = null;
    email = null;
    firstName = null;
    lastName = null;
    gender = null;
    personID = null;
  }

  /**
   * Accepts the incoming user information and creates a pojo.
   *
   * @param setUsername  the user's username.
   * @param setPassword  the user's password.
   * @param setEmail     the user's email.
   * @param setFirstName the user's first name.
   * @param setLastName  the user's last name.
   * @param setGender    the user's gender.
   * @param setPersonID  Unique Person ID assigned to this user’s generated Person object.
   */
  public User(String setUsername, String setPassword, String setEmail, String setFirstName, String setLastName,
              String setGender, String setPersonID) {
    this.username = setUsername;
    this.password = setPassword;
    this.email = setEmail;
    this.firstName = setFirstName;
    this.lastName = setLastName;
    this.gender = setGender;
    this.personID = setPersonID;
  }

  public String getUsername() { return username; }
  public String getPassword() { return password; }
  public String getEmail() { return email; }
  public String getFirstName() { return firstName; }
  public String getLastName() { return lastName; }
  public String getGender() { return gender; }
  public String getPersonID() { return personID; }

  public void setUsername(String setUsername) { this.username = setUsername; }
  public void setPassword(String setPassword) { this.password = setPassword; }
  public void setEmail(String setEmail) { this.email = setEmail; }
  public void setFirstName(String setFirstName) { this.firstName = setFirstName; }
  public void setLastName(String setLastName) { this.lastName = setLastName; }
  public void setGender(String setGender) { this.gender = setGender; }
  public void setPersonID(String setPersonID) { this.personID = setPersonID; }

  @Override
  public boolean equals(Object o) {
    if (o == null)
      return false;
    if (o instanceof User) {
      User oUser = (User) o;
      return oUser.getUsername().equals(getUsername()) &&
              oUser.getPassword().equals(getPassword()) &&
              oUser.getEmail().equals(getEmail()) &&
              oUser.getFirstName().equals(getFirstName()) &&
              oUser.getLastName().equals(getLastName()) &&
              oUser.getGender().equals(getGender()) &&
              oUser.getPersonID().equals(getPersonID());
    } else {
      return false;
    }
  }
}
