package server.items;

// uses singleton pattern
public class Good extends Item {
  public Good(String name, int amount, String type, String description, Double price) {
    super(name, amount, type, description, price);
  }
} 