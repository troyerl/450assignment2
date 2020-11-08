package server;

import java.rmi.registry.Registry; 
import java.rmi.registry.LocateRegistry; 
import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject;

import common.ConnectionRequests;

public class RemoteServer extends ConnectionRequestsImpl { 
  public RemoteServer(){}

   public static void main(String args[]) { 
      try { 
         // Instantiating the implementation class 
         ConnectionRequestsImpl obj = new ConnectionRequestsImpl(); 
    
         // Exporting the object of implementation class  
         // (here we are exporting the remote object to the stub) 
         ConnectionRequests stub = (ConnectionRequests) UnicastRemoteObject.exportObject(obj, 0);  
         
         // Binding the remote object (stub) in the registry 
         Registry registry = LocateRegistry.getRegistry(); 
         
         registry.bind("ConnectionRequests", stub);  

         System.err.println("Server ready"); 
      } catch (Exception e) { 
         System.err.println("Server exception: " + e.toString()); 
         e.printStackTrace(); 
      } 
   } 
}