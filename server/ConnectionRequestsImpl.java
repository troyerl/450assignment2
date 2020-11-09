package server;
import java.util.UUID;
import java.util.ArrayList;
import java.util.HashMap;

import common.ConnectionRequests;
import server.items.Item;
import server.users.Customer;
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

  public boolean addItemToCart(String itemId, ArrayList<String> item, UUID customerId) {
    inventoryList.updateItemAmount(UUID.fromString(itemId), Integer.parseInt(item.get(1)));
    authentication.addItemToCart(customerId, item, UUID.fromString(itemId));
    return true;
  }

  public String checkout(UUID id) {
    Customer user = authentication.getCustomer(id);
    HashMap<UUID,ArrayList<String>> userCart = null;
    String receipt= "Receipt \n________________________\n";
    if (user != null) {
      userCart = user.getCart();

      for (ArrayList<String> item : userCart.values()) {
        receipt += "Item: " + item.get(0) + "\nAmount: " + item.get(1) + "\nPrice: " + item.get(4) + "\n_______________\n";
      }

      user.clearCart();
    }
    return receipt;
  }
} 