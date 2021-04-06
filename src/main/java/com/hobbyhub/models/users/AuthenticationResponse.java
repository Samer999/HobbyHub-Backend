package com.hobbyhub.models.users;

import lombok.Data;

@Data
public class AuthenticationResponse {
  private String message;
  private String token;
  private UserModel userModel;

  public AuthenticationResponse() {}

  public AuthenticationResponse(String message) {
    this.message = message;
  }

  public AuthenticationResponse(String message, String token, UserModel userModel) {
    this.message = message;
    this.token = token;
    this.userModel = userModel;
  }
}
