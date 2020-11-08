package server.items;

// uses singleton pattern
public class Service extends Item {
  public Service(String name, int amount, String type, String description, Double price) {
    super(name, amount, type, description, price);
  }
} 