package client.requests;

import common.ConnectionRequests;
import java.util.UUID;

public class CustomerRequests extends Requests {
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
          break;
        // Checkout
        case 3:
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
} 