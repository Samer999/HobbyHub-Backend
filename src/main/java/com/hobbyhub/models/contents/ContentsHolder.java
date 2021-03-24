package com.hobbyhub.models.contents;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ContentsHolder implements IContentsHolder {

  private List<ContentPair> contentList;

  public ContentsHolder() {
    contentList = new ArrayList<>();
  }

  /** @return A deep copy of content list. */
  @Override
  public List<ContentPair> getContentList() {
    List<ContentPair> copiedContentList = new ArrayList<>();
    copiedContentList.addAll(contentList);
    return copiedContentList;
  }


  @Override
  public boolean addContent(ContentPair contentPair) {
    if (contentPair == null) {
      return false;
    }
    contentList.add(contentPair);
    return true;
  }

  @Override
  public boolean removeContent(ContentPair contentPair) {
    return contentList.remove(contentPair);
  }

  @Override
  public boolean removeContent(int index) {
    if (index < 0 || index >= contentList.size()) {
      throw new IndexOutOfBoundsException(String.format("index[%s] value is not valid", index));
    }
    contentList.remove(index);
    return true;
  }

  @Override
  public int getNumberOfContent() {
    return contentList.size();
  }
}
