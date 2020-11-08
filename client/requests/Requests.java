package client.requests;

import common.ConnectionRequests;
import java.util.UUID;

abstract class Requests {
  protected ConnectionRequests stub;
  protected UUID id;

  public Requests (ConnectionRequests stub, UUID id) {
    this.stub = stub;
    this.id = id;
  }

  abstract void makeRequest(int userChoice);
} 