package client;

import java.rmi.registry.LocateRegistry; 
import java.rmi.registry.Registry;

import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader;
import java.util.UUID;

import common.ConnectionRequests;

import client.requests.AdminRequests;
import client.requests.CustomerRequests;

public class Client {   

   public static void main(String[] args) {  
      try {  
         // Getting the registry 
         Registry registry = LocateRegistry.getRegistry(null); 
         // Looking up the registry for the remote object 
         ConnectionRequests stub = (ConnectionRequests) registry.lookup("ConnectionRequests"); 

         UUID userId = startScreen(stub);
         boolean isAdmin = stub.checkIfAdmin(userId);

         if (isAdmin) {
            adminScreen(stub, userId);
         } else {
            customerScreen(stub, userId);
         }


      } catch (Exception e) {
         System.err.println("Client exception: " + e.toString()); 
         e.printStackTrace(); 
      } 
   }

   public static UUID startScreen(ConnectionRequests stub) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      UUID id = null;
      boolean hasId = false;
      String username, password;
      int userChoice;

      System.out.println("Welcome!");

      while(!hasId) {
         try {
            String[] options = {"Browse Items", "Login", "Register"};
            userChoice = menuOptions(options);
            switch (userChoice) {
               case 1:
                  System.out.println(stub.browseItems(null));
                  break;
               case 2:
                  System.out.print("Enter username: ");
                  username = reader.readLine();

                  System.out.print("Enter password: ");
                  password = reader.readLine();
   
                  id = stub.loginUser(username.trim(), password.trim());
                  break;
               case 3:
                  System.out.print("Enter first name: ");
                  String firstName = reader.readLine();

                  System.out.print("Enter last name: ");
                  String lastName = reader.readLine();

                  System.out.print("Enter username: ");
                  username = reader.readLine();

                  System.out.print("Enter password: ");
                  password = reader.readLine();

                  id = stub.registerUser(firstName.trim(), lastName.trim(), username.trim(), password.trim());
                  break;
               case 4:
                  System.exit(0);
                  break;
               default:
                  System.out.println("Choice Not Found Please Try Again");
                  break;
            }

            if (id != null) {
               System.out.println("\nLogin Complete.");
               hasId = true;
            } else {
               if (userChoice == 2) {
                  System.out.println("\nLogin info incorrect, please try again.");
               } else if (userChoice == 3) {
                  System.out.println("\nUser with that username already exists, please try again.");
               }
            }
         } catch (Exception e) {
            System.err.println("Client exception: " + e.toString()); 
            e.printStackTrace();
         } 
      }

      return id;
   }


   // displays admin screen and sends users choice to admin requests
   public static void adminScreen(ConnectionRequests stub, UUID id) {
      AdminRequests request = new AdminRequests(stub, id);
      String[] options = {"Browse Items", "Add New Item", "Update Item", "Remove Item", "Add New Admin", "Add New Customer", "Remove User"};
      boolean run = true;
      while(run) {
         int choice = menuOptions(options);
         request.makeRequest(choice);
      }
   }

   // displays customer screen and sends users choice to customer requests
   public static void customerScreen(ConnectionRequests stub, UUID id) {
      CustomerRequests request = new CustomerRequests(stub, id);
      boolean run = true;
      String[] options = {"Browse Items", "Add Item To Cart", "Checkout"};
      while(run) {
         int choice = menuOptions(options);
         request.makeRequest(choice);
      }
   }

   public static int menuOptions(String[] optionArray) {
      boolean showMenuOptions = true;
      int input = -1;
      while(showMenuOptions) {
         try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("");
            
            for (int i = 0; i < optionArray.length; i++) {
               System.out.println((i + 1) + ".) " + optionArray[i]);
            }
            System.out.print((optionArray.length + 1) + ".) Exit \nEnter your selection: ");
   
            input = Integer.parseInt(reader.readLine());

            System.out.println("");

            if (input > 0 && input <= optionArray.length + 1) {
               showMenuOptions = false;
            } else {
               System.out.println("Please input correct choice");
            }
         } catch(Exception e) {
            System.out.println("Please input correct choice");
         }
      }

      return input;
   }
}