package com.hobbyhub.test;

import lombok.Data;

@Data
public class Compi {

  private String tests;

  public Compi() {
    this.tests = "lol";
  }

  public Compi(String s) {
    this.tests = s;
  }
}
