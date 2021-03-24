package com.hobbyhub.models.contents;

import org.springframework.stereotype.Component;

@Component("TextContentHolder")
public class TextContentsHolder extends ContentsHolder {

  @Override
  public boolean addContent(ContentPair contentPair) {
    if (contentPair.getContentType() != ContentType.TEXT) {
      throw new IllegalArgumentException(
          "content type to add is not compatible with content holder.");
    }
    return super.addContent(contentPair);
  }
}
