package com.hobbyhub.models.users;

import lombok.Data;

@Data
public class SignUpRequest {
  private String username;
  private String password;
  private String email;
  private String firstName;
  private String lastName;
  private String gender;
  private String imageUrl;
}
