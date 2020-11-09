package server;

import java.util.HashMap;
import java.util.UUID;
import java.util.ArrayList;

import server.items.*;

// uses singleton pattern
public class Inventory {
  private HashMap<UUID, Item> inventory = new HashMap<UUID, Item>();
  private Auth authentication = Auth.getInstance();

  private static Inventory single_instance = null; 

  private Inventory() {
    this.inventory.put(UUID.randomUUID(), new Good("Wireless Mouse", 20, "Electronic", "A wireless mouse for computers", 41.13));
    this.inventory.put(UUID.randomUUID(), new Good("Gaming Headset", 4, "Electronic", "A gaming headset for computers", 84.84));
    this.inventory.put(UUID.randomUUID(), new Good("Case of Coke", 100, "Liquid", "Pack of 18", 19.05));
    this.inventory.put(UUID.randomUUID(), new Good("Case of Coke", 100, "Liquid", "Pack of 18", 19.05));
    this.inventory.put(UUID.randomUUID(), new Good("Reversible L-shaped Sectional Sofa Set", 10, "Furniture", "Savor the refined, classically subdued glamour of this L-shaped sectional sofa set with reversible chaise lounge", 448.49));
    this.inventory.put(UUID.randomUUID(), new Good("A5 Kobe Beef Ribeye Steak", 200, "Meat", "Kobe is sourced from only 5,500 qualifying head of cattle per year, of which only 10% are eligible for foreign export. This center cut A5 Kobe Beef Ribeye Steak is not only rare, but it is truly superior.", 426.56));
  }

  // static method to create instance of inventory class 
	public static Inventory getInstance() { 
		if (single_instance == null) 
			single_instance = new Inventory(); 

		return single_instance; 
  }
  
  public String getInventoryList(UUID id) {
    String inventoryString = "Items\n___________________________\n";
    Item inventoryItem;

    boolean isAdmin = false;

    if (id != null) {
      isAdmin= authentication.checkIfAdmin(id);
    } else {
      isAdmin = false;
    }

    for (UUID i : this.inventory.keySet()) {
      inventoryItem = this.inventory.get(i);
      
      inventoryString = inventoryString + (isAdmin ? "ID: " + i.toString() + "\n": "") +"Item: " + inventoryItem.getName() + "\nAmount: " + inventoryItem.getAmount() + "\nPrice: " + inventoryItem.getPrice() + "\nType: " + inventoryItem.getType() + "\nDescription: " + inventoryItem.getDescription() + "\n___________________________\n";
    }

    return inventoryString;
  }

  public HashMap<String, ArrayList<String>> getItem(String itemName) {
    Item inventoryItem;
    HashMap<String, ArrayList<String>> items = new HashMap<String, ArrayList<String>>();

    for (UUID i : this.inventory.keySet()) {
      inventoryItem = this.inventory.get(i);
      
      if (itemName.equals(inventoryItem.getName())) {
        ArrayList<String> item = new ArrayList<String>();

        item.add(inventoryItem.getName());
        item.add(String.valueOf(inventoryItem.getAmount()));
        item.add(inventoryItem.getType());
        item.add(inventoryItem.getDescription());
        item.add(String.valueOf(inventoryItem.getPrice()));

        items.put(i.toString(), item);
      }
    }
    return items;
  }

  public void updateItem(ArrayList<String> item, UUID id) {
    Item newItem = this.inventory.get(id);
    try {
      newItem.setName(item.get(0));
      newItem.setAmount((int)Double.parseDouble(item.get(1)));
      newItem.setType(item.get(2));
      newItem.setDescription(item.get(3));
      newItem.setPrice(Double.parseDouble(item.get(4)));
    } catch(NumberFormatException e) {
      Double amount = Double.parseDouble(item.get(1));
    }

    System.out.println("item has been updated with ID: " + id.toString());
  }

  public void addItem(ArrayList<String> item) {
    Good newItem = new Good(item.get(0), (int)Double.parseDouble(item.get(1)), item.get(2), item.get(3), Double.parseDouble(item.get(4)));

    UUID id = UUID.randomUUID();

    boolean checkIdAvailability = true;
    while(checkIdAvailability) {
      if (this.inventory.get(id) == null) {
        checkIdAvailability = false;
      } else {
        id = UUID.randomUUID();
      }
    }
    this.inventory.put(id, newItem);
    System.out.println("item created with ID: " + id.toString());
  }

  public boolean removeItem(String id) {
    Item deleteItem = this.inventory.get(UUID.fromString(id));
    if (deleteItem == null) {
      return false;
    }

    this.inventory.remove(UUID.fromString(id));
    return true;
  }

  public void updateItemAmount(UUID id, int amount) {
    Item updateItem = this.inventory.get(id);
    updateItem.updateAmount(amount);
  }
} 