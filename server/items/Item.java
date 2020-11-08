package server.items;

import java.util.HashMap;
import java.util.UUID;

// uses singleton pattern
public class Item {
  private String name;
  private int amount;
  private String type;
  private String description;
  private Double price;

  public Item(String name, int amount, String type, String description, Double price) {
    this.name = name;
    this.amount = amount;
    this.type = type;
    this.description = description;
    this.price = price;
  }

  public String getName() {
    return this.name;
  }

  public int getAmount() {
    return this.amount;
  }

  public String getType() {
    return this.type;
  }

  public String getDescription() {
    return this.description;
  }

  public double getPrice() {
    return this.price;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setPrice(Double price) {
    this.price = price;
  }
} 