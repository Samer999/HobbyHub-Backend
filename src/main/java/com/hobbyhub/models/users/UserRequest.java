package com.hobbyhub.models.users;

import lombok.Data;

@Data
public class UserRequest {
  private String username;
  private String firstName;
  private String lastName;
  private String password;
  private String email;
  private String gender;
  private String imageUrl;
}
