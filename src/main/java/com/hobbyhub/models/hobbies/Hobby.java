package com.hobbyhub.models.hobbies;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "hobbies")
public class Hobby {
  @Id private String id;
  @NonNull private String name;
  private String description;
  private String image;

  public Hobby() {

  }
}
