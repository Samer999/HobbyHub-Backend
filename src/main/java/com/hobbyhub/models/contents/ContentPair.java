package com.hobbyhub.models.contents;

import lombok.Data;

@Data
public class ContentPair {
  private ContentType contentType;
  private String value;

  public ContentPair(ContentType contentType, String value) {
    this.contentType = contentType;
    this.value = value;
  }

  public ContentPair(){}
}
