package server.users;

import java.util.HashMap;
import java.util.UUID;
import java.util.ArrayList;

import server.items.Item;

public class Customer extends User {
  private HashMap<UUID,ArrayList<String>> cart = new HashMap<UUID,ArrayList<String>>();
  
  public Customer (String firstName, String lastName, String username, String password) {
    super(firstName, lastName, username, password);
  }

  public void updateItemInCart(UUID itemId, ArrayList<String> item) {
    ArrayList<String> matchedItem = this.cart.get(itemId);

    if (matchedItem == null) {
      this.cart.put(itemId, item);
    } else {
      int amount = Integer.parseInt(matchedItem.get(1)) + Integer.parseInt(item.get(1));
      matchedItem.set(1, Integer.toString(amount));
    }
  }

  public HashMap<UUID,ArrayList<String>> getCart() {
    return this.cart;
  }

  public void clearCart() {
    this.cart.clear();
  }
} 