package com.hobbyhub.models.hobbies;

import lombok.Data;

@Data
public class HobbyRequest {
  private String id;
  private String name;
  private String description;
  private String image;
}
