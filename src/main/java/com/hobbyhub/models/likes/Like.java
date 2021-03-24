package com.hobbyhub.models.likes;

import com.hobbyhub.models.users.UserCreation;
import java.util.Date;
import lombok.Data;

@Data
public class Like extends UserCreation {

  private String likeId;

  public Like(String creatorUsername, Date dateCreated) {
    super(creatorUsername, dateCreated);
    this.likeId = creatorUsername;
  }

  public Like() {
  }
}
