package com.hobbyhub.test;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
@Data
public class DataContext extends ParentData{


  @Id
  private String id;
  private String name;
  private Compi compi = new Compi("nooo");
}
