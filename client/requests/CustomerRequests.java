package client.requests;

import common.ConnectionRequests;
import java.util.UUID;
import java.io.BufferedReader; 
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerRequests extends Requests {
  private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

  public CustomerRequests (ConnectionRequests stub, UUID id) {
    super(stub, id);
  }

  public void makeRequest(int userChoice) {
    try {

      switch(userChoice) {
        // Browse Items
        case 1:
          System.out.println(stub.browseItems(this.id));
          break;

        // Add Item To Cart
        case 2:
          addItemToCart();
          break;

        // Checkout
        case 3:
          checkout();
          break;

        // Exit
        case 4:
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

  private void addItemToCart() {
    System.out.print("\nInput a item name to add to your cart (Type 'Cancel' if you wish to go to the main screen): ");
    String input = readUserInput();
    String id = null;
    if (!input.trim().toLowerCase().equals("cancel")) {
      try {
        HashMap<String, ArrayList<String>> items = this.stub.requestItem(input);
        ArrayList<String> item = null;


        if (items.size() == 0) {
          System.out.println("");
          System.out.println("No items found with that name, please try again.");
          System.out.println("");
        } else if (items.size() > 1) {
          listItems(items);
          boolean getOption = true;
          while(getOption) {
            System.out.print("Two items with this name have been found, please input the items 'Option Number' you would like to update (Type 'Cancel' if you wish to go to the main screen): ");
            input = readUserInput();
            if (input.toLowerCase().equals("cancel")){
              break;
            } else {
              try {
                int index = Integer.parseInt(input);
                int option = 1;
                for (String i : items.keySet()) {
                  if (index == option) {
                    id = i;
                    item = items.get(i);
                    break;
                  }
                  option++;
                }
                if (item != null) {
                  getOption = false;
                } else {
                  System.out.println("Invalid option, please try again.\n");
                }
              } catch (Exception e) {
                System.out.println("Invalid option, please try again.\n");
              }
            }
          }
        } else {
          listItems(items);
          id = items.keySet().stream().findFirst().get();
          item = items.get(id);
        }
        boolean getAmount = true;
        while(getAmount) {
          try {
            System.out.print("\nEnter the the number you would like to purchase: ");
            input = readUserInput();
            if (Integer.parseInt(input) <= Integer.parseInt(item.get(1))) {
              item.set(1, input);
              getAmount = false;
            } else {
              System.out.println("\nAmount larger than what we have in stock, please try again.");
            }
          } catch(Exception e) {
            System.out.println("Invaild input, please try again.");
          }
        }

        boolean updateCheck = this.stub.addItemToCart(id, item, this.id);

        if (updateCheck) {
          System.out.println("\nItem has been added to your cart.");
        } else {
          System.out.println("\nError when trying to add item it your cart, please try again.");
        }

      } catch (Exception e) {
        System.out.println(e);
      }
    }
  }

  private void listItems(HashMap<String, ArrayList<String>> items) {
    ArrayList<String> item = null;
    int optionNumber = 1;
    for (String i : items.keySet()) {
      item = items.get(i);
      System.out.println("_______________________________________");
      System.out.println("Option Number: "+ optionNumber);
      System.out.println("Name: " + item.get(0));
      System.out.println("Amount: " + item.get(1));
      System.out.println("Type: " + item.get(2));
      System.out.println("Description: " + item.get(3));
      System.out.println("Price: " + item.get(4));
      optionNumber++;
    }
  } 

  private void checkout() {
    try {
      String receipt = this.stub.checkout(this.id);
      System.out.print(receipt);
    } catch (Exception e) {
      System.out.println(e);
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
} 