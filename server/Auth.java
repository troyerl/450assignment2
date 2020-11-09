package server;

import java.util.HashMap;
import java.util.UUID;
import java.util.ArrayList;

import server.users.*;

// uses singleton pattern
public class Auth {
  private HashMap<UUID, User> users = new HashMap<UUID, User>();

  private static Auth single_instance = null; 

  private Auth() {
    this.users.put(UUID.randomUUID(), new Admin("john", "smith", "jsmith", "test123"));
  }

  // static method to create instance of Singleton class 
	public static Auth getInstance() { 
		if (single_instance == null) 
			single_instance = new Auth(); 

		return single_instance; 
	} 

  public UUID login(String username, String password) {
    User userInfo;
    for (UUID i : this.users.keySet()) {
      userInfo = this.users.get(i);

      if (username.equals(userInfo.getUsername())) {
        if (password.equals(userInfo.getPassword())) {
          System.out.println("user logged in with ID: " + i.toString());
          return i;
        }
      }
    }

    return null;
  }

  public UUID register(String firstName, String lastName, String username, String password) {
    UUID id = UUID.randomUUID();
    User userInfo;
    boolean checkIdAvailability = true, usernameAlreadyExists = false;

    // checks if id already exists
    while(checkIdAvailability) {
      userInfo = this.users.get(id);
      if (userInfo == null) {
        checkIdAvailability = false;
      } else {
        id = UUID.randomUUID();
      }
    }

    // loop check is saved user already has that username
    for (UUID i : this.users.keySet()) {
      usernameAlreadyExists = username.equals(this.users.get(i).getUsername());
      if (usernameAlreadyExists) {
        break;
      }
    }

    if (!usernameAlreadyExists) {
      this.users.put(id, new Customer(firstName, lastName, username, password));
      System.out.println("user created with ID: " + id.toString());
    } else {
      id = null;
    }

    return id;
  }

  public boolean checkIfAdmin(UUID id) {
    return this.users.get(id) instanceof Admin;
  }

  public boolean addUser(ArrayList<String> newUser, boolean isAdmin) {
    User userInfo = null;

    for (UUID i : this.users.keySet()) {
      if (newUser.get(2).equals(this.users.get(i).getUsername())) {
        return false;
      }
    }

    // checks if id already exists
    UUID id = UUID.randomUUID();
    boolean checkIdAvailability = true;
    while(checkIdAvailability) {
      userInfo = this.users.get(id);
      if (userInfo == null) {
        checkIdAvailability = false;
      } else {
        id = UUID.randomUUID();
      }
    }

    if (isAdmin) {
      this.users.put(id, new Admin(newUser.get(0), newUser.get(1), newUser.get(2), newUser.get(3)));
    } else {
      this.users.put(id, new Customer(newUser.get(0), newUser.get(1), newUser.get(2), newUser.get(3)));
    }
    System.out.println((isAdmin ? "Admin" : "Customer") + " created with ID: " + id);
    return true;
  }

  // finds user with specific username and checks if its a customer
  public boolean removeCustomer(String customerUsername) {
    User user = null;

    for (UUID i : this.users.keySet()) {
      user = this.users.get(i);
      if (customerUsername.equals(user.getUsername())) {
        if (user instanceof Customer) {
          this.users.remove(i);
          return true;
        } else {
          return false;
        }
      }
    }

    return false;
  }

  public Customer getCustomer(UUID id) {
    return (Customer) this.users.get(id);
  }

  public void addItemToCart(UUID customerId, ArrayList<String> item, UUID itemId) {
    Customer currentUser = (Customer) this.users.get(customerId);

    currentUser.updateItemInCart(itemId, item);
  }
} 