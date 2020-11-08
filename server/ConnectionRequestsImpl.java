package server;
import java.util.UUID;
import java.util.ArrayList;
import java.util.HashMap;

import common.ConnectionRequests;
import server.items.Item;

// Implementing the remote interface 
public class ConnectionRequestsImpl implements ConnectionRequests {
  private Auth authentication = Auth.getInstance();
  private Inventory inventoryList = Inventory.getInstance();
  
  public ConnectionRequestsImpl () {}

  public UUID loginUser(String username, String password) {
    return authentication.login(username, password);
  }

  public UUID registerUser(String firstName, String lastName, String username, String password) {
    return authentication.register(firstName, lastName, username, password);
  }

  public String browseItems(UUID id) {
    return inventoryList.getInventoryList(id);
  }

  public boolean checkIfAdmin(UUID id) {
    return authentication.checkIfAdmin(id);
  }

  public HashMap<String, ArrayList<String>> requestItem(String itemName) {
    return inventoryList.getItem(itemName);
  }

  public void updateItem(ArrayList<String> item, String id) {
    inventoryList.updateItem(item, UUID.fromString(id));
  }

  public void addItem(ArrayList<String> item) {
    inventoryList.addItem(item);
  }

  public boolean removeItem(String id) {
    return inventoryList.removeItem(id);
  }

  public boolean addUser(ArrayList<String> newUser, boolean isAdmin) {
    return authentication.addUser(newUser, isAdmin);
  }

  public boolean removeCustomer(String customerUsername) {
    return authentication.removeCustomer(customerUsername);
  }
} 