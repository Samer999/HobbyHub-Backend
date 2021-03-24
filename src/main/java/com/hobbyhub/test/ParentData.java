package com.hobbyhub.test;

import java.util.Date;
import lombok.Data;

@Data
public class ParentData {
  private String creatorUsername;
  private Date dateCreated;

  public ParentData() {
  }

  public ParentData(String creatorUsername, Date dateCreated) {
    this.creatorUsername = creatorUsername;
    this.dateCreated = dateCreated;
  }
}