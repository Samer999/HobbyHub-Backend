package com.hobbyhub.models.users;

import lombok.Data;

@Data
public class SignInRequest {
  private String username;
  private String password;
}
