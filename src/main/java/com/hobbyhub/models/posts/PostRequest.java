package com.hobbyhub.models.posts;

import com.hobbyhub.models.contents.ContentPair;
import java.util.List;
import lombok.Data;

@Data
public class PostRequest {

  private String id;
  private List<ContentPair> contentList;
  private List<String> categories;
}
