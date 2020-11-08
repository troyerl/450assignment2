package client.requests;

import java.io.BufferedReader; 
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.ArrayList;
import java.util.HashMap;

import common.ConnectionRequests;
import server.items.Item;

public class AdminRequests extends Requests {
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  public AdminRequests (ConnectionRequests stub, UUID id) {
    super(stub, id);
  }

  public void makeRequest(int userChoice) {
    try {
      switch(userChoice) {
        // Browse Items
        case 1:
          System.out.println(this.stub.browseItems(this.id));
          break;
        // Add Item
        case 2:
          addItem();
          break;
        // Update Item
        case 3:
          updateItem();
          break;
        // Remove Item
        case 4:
          deleteItem();
          break;
        // Add New Admin
        case 5:
          break;
        // Remove Admin
        case 6:
          break;
        // Exit
        case 7:
          System.exit(0);
          break;
        default:
          System.out.println("Choice Not Found Please Try Again");
          break;
      }
    } catch(Exception e) {
      System.err.println("Client exception: " + e.toString()); 
      e.printStackTrace();
    }
  }

  private boolean updateItem() {
    boolean run = true;
    String itemName = null;
    String id = null;
    ArrayList<String> item = null;
    while(item == null) {
      System.out.print("Input Item Name to Upate it (Type 'Cancel' if you wish to go to the main screen): ");
      itemName = readUserInput();
      if (itemName.toLowerCase().trim().equals("cancel")) {
        return true;
      } else {
        try {
          HashMap<String, ArrayList<String>> items = this.stub.requestItem(itemName);
          if (items.size() == 0) {
            System.out.println("");
            System.out.println("No items found with that name, please try again.");
            System.out.println("");
            continue;
          } else if (items.size() > 1) {
            listItems(items);
            boolean getId = true;
            while(getId) {
              System.out.print("\nTwo items with this name have been found, please input the items ID you would like to update (Type 'Cancel' if you wish to go to the main screen): ");
              id = readUserInput();
              if (id.toLowerCase().equals("cancel")){
                return true;
              } else {
                item = items.get(id.trim());
                if (item == null) {
                  System.out.println("\nItem with that ID does not exist, please try again.\n");
                } else {
                  getId = false;
                }
              }
            }
          } else {
            listItems(items);
            id = items.keySet().stream().findFirst().get();;
            item = items.get(id);
          }
        } catch(Exception e) {
          System.err.println("Client exception: " + e.toString()); 
          e.printStackTrace();
        }
      }
    }

    System.out.println("\nTo update please type in the new value, if you don't wish to update then leave blank\n");
    System.out.print("Name: ");
    String name = readUserInput();
    
    boolean amountCheck = true;
    String amount = "";
    while(amountCheck) {
      try {
        System.out.print("Amount: ");
        amount = readUserInput();
        if (amount != "") {
          Double checkingType = Double.parseDouble(amount);
        }
        amountCheck = false;
      } catch(Exception e) {
        System.out.println("Incorrect input type");
      }
    }

    System.out.print("Type: ");
    String type = readUserInput();
    System.out.print("Description: ");
    String description = readUserInput();
    

    boolean priceCheck = true;
    String price = "";
    while(priceCheck) {
      try {
        System.out.print("Price: ");
        price = readUserInput();
        if (price != "") {
          Double checkingType = Double.parseDouble(amount);
        }
        priceCheck = false;
      } catch(Exception e) {
        System.out.println("Incorrect input type");
      }
    }

    if (name != "") {
      item.set(0, name);
    }
    if (amount != "") {
      item.set(1, amount);
    }
    if (type != "") {
      item.set(2, type);
    }
    if (description != "") {
      item.set(3, description);
    }
    if (price != "") {
      item.set(4, price);
    }

    try {
      this.stub.updateItem(item, id);
      System.out.println("\nItem has been updated!");
    } catch (Exception e) {
      System.err.println("Client exception: " + e.toString()); 
      e.printStackTrace();
    }
    
    return true;
  }

  private void listItems(HashMap<String, ArrayList<String>> items) {
    ArrayList<String> item = null;
    for (String i : items.keySet()) {
      item = items.get(i);
      System.out.println("_______________________________________");
      System.out.println("ID: "+ i);
      System.out.println("Name: " + item.get(0));
      System.out.println("Amount: " + item.get(1));
      System.out.println("Type: " + item.get(2));
      System.out.println("Description: " + item.get(3));
      System.out.println("Price: " + item.get(4));
    }
  } 

  private String readUserInput() {
    String input = null;
    try {
      input = reader.readLine();
    } catch(Exception e) {
      System.out.println("Please input correct choice");
    }

    return input;
  }

  public void addItem() {
    System.out.println("\nAdd New Item\n___________________\n");
    String name = "", amount = "", type = "", description = "", price = "";
    ArrayList<String> newItem = new ArrayList<String>();
    
    while(name == "") {
      System.out.print("Name (Required): ");
      name = readUserInput();
    }
    newItem.add(name);

    while(amount == "") {
      try {
        System.out.print("Amount (Required): ");
        amount = readUserInput();
        Double checkingType = Double.parseDouble(amount);
      } catch(Exception e) {
        System.out.println("Incorrect input type");
        amount = "";
      }
    }
    newItem.add(amount);

    while(type == "") {
      System.out.print("Type (Required): ");
      type = readUserInput();
    }
    newItem.add(type);

    while(description == "") {
      System.out.print("Description (Required): ");
      description = readUserInput();
    }
    newItem.add(description);

    while(price == "") {
      try {
        System.out.print("Price (Required): ");
        price = readUserInput();
        Double checkingType = Double.parseDouble(price);
      } catch(Exception e) {
        System.out.println("Incorrect input type");
        price = "";
      }
    }
    newItem.add(price);

    try {
      this.stub.addItem(newItem);
      System.out.println("\nItem has been added!\n");
    } catch(Exception e) {
      System.out.println("Error");
    }
  }

  public void deleteItem() {
    
    boolean deleteCheck = true;
    while(deleteCheck) {
      try {
        System.out.print("Input item ID to delete it (Type 'Cancel' if you wish to go to the main screen): ");
        String id = readUserInput();
        if (id.toLowerCase().equals("cancel")) {
          break;
        }

        UUID testId = UUID.fromString(id);
        boolean responseCheck = this.stub.removeItem(id);
        
        if (responseCheck) {
          deleteCheck = !responseCheck;
          System.out.println("\nItem has been deleted.");
        } else {
          System.out.println("\nItem with that ID does not exist, please try again.\n");
        }
      } catch (Exception e) {
        System.out.println("\nInvaild type, please try again\n");
      }
    }
  }
} 