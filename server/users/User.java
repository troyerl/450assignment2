package server.users;

public class User {
  private String firstName;
  private String lastName;
  private String username;
  private String password;

  public User (String firstName, String lastName, String username, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return this.username;
  }

  public String getPassword() {
    return this.password;
  }

  public String getName() {
    return this.firstName + " " + this.lastName;
  }
} 