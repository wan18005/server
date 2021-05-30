package Models;


public class User
{

  private String username;
  private String password;
  private String email;
  private String firstName;
  private String lastName;
  private String gender;
  private String personID;

  /**
   * This is the default constructor
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
   * constructor
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

  public void setUsername(String Username) { this.username = Username; }
  public void setPassword(String Password) { this.password = Password; }
  public void setEmail(String Email) { this.email = Email; }
  public void setFirstName(String FirstName) { this.firstName = FirstName; }
  public void setLastName(String LastName) { this.lastName = LastName; }
  public void setGender(String Gender) { this.gender = Gender; }
  public void setPersonID(String PersonID) { this.personID =PersonID ; }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User userModel = (User) o;
    return username.equals(userModel.username) && password.equals(userModel.password) && email.equals(userModel.email) && firstName.equals(userModel.firstName) && lastName.equals(userModel.lastName) && gender.equals(userModel.gender) && personID.equals(userModel.personID);
  }
}
