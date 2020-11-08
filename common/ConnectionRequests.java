package common;

import java.rmi.Remote; 
import java.rmi.RemoteException;
import java.util.UUID;
import java.util.ArrayList;
import java.util.HashMap;

import server.items.Item;

// Creating Remote interface for our application 
public interface ConnectionRequests extends Remote {  
  UUID loginUser(String username, String password) throws RemoteException; 
  UUID registerUser(String firstName, String lastName, String username, String password) throws RemoteException;
  String browseItems(UUID id) throws RemoteException;
  boolean checkIfAdmin(UUID id) throws RemoteException;
  HashMap<String, ArrayList<String>> requestItem(String itemName) throws RemoteException;
  void updateItem(ArrayList<String> item, String id) throws RemoteException;
  void addItem(ArrayList<String> item) throws RemoteException;
  boolean removeItem(String id) throws RemoteException;
} 