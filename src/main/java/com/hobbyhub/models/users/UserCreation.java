package com.hobbyhub.models.users;

import java.util.Date;
import lombok.Data;

@Data
public abstract class UserCreation {

  private String creatorUsername;
  private Date dateCreated;

  public UserCreation() {
  }

  public UserCreation(String creatorUsername, Date dateCreated) {
    this.creatorUsername = creatorUsername;
    this.dateCreated = dateCreated;
  }
}
