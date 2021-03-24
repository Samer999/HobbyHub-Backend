package com.hobbyhub.models.users;

import lombok.Data;

@Data
public class AuthenticationResponse {
  private String message;
  private String token;

  public AuthenticationResponse() {}

  public AuthenticationResponse(String message) {
    this.message = message;
  }

  public AuthenticationResponse(String message, String token) {
    this.message = message;
    this.token = token;
  }
}
