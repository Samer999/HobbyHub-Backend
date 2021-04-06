package com.hobbyhub.models.contents;

import java.util.List;

public interface IContentsHolder {

  List<ContentPair> getContentList();

  void setContentList(List<ContentPair> contentList);

  boolean addContent(ContentPair contentPair);

  boolean removeContent(ContentPair contentPair);

  boolean removeContent(int index);

  int getNumberOfContent();
}
