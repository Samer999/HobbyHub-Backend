package com.hobbyhub.models.contents;

public enum ContentType {
  TEXT("text"),
  VIDEO("video"),
  IMAGE("image"),
  LOCATION("location");

  private String label;

  ContentType(String label) {
    this.label = label;
  }

  ContentType() {
    label = "text";// default value
  }

  @Override
  public String toString() {
    return label;
  }

  public static ContentType fromValueToEnum(String value) {
    switch (value) {
      case "text": return ContentType.TEXT;
      case "video": return ContentType.VIDEO;
      case "image": return ContentType.IMAGE;
      case "location": return ContentType.LOCATION;
    }
    return null;
  }
}
