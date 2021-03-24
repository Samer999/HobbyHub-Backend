package com.hobbyhub.models.comments;

import com.hobbyhub.models.contents.ContentPair;
import java.util.List;
import lombok.Data;

@Data
public class CommentRequest {
  private String postId;
  private String commentId;
  private String creatorUsername;
  private List<ContentPair> contentList;
}
