package com.hobbyhub.models.hobbies;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "hobbies")
public abstract class Hobby {

  private String name;
  private String description;
  private String image;
}
