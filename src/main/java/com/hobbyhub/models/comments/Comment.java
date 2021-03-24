package com.hobbyhub.models.comments;

import com.hobbyhub.models.contents.ContentPair;
import com.hobbyhub.models.contents.ContentsHolder;
import com.hobbyhub.models.contents.IContentsHolder;
import com.hobbyhub.models.users.UserCreation;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Data;

public class Comment extends UserCreation implements IContentsHolder {

  private String commentId;
  private ContentsHolder contentsHolder;

  public Comment(String creatorUsername, Date dateCreated) {
    super(creatorUsername, dateCreated);
    contentsHolder = new ContentsHolder();
    this.commentId = UUID.randomUUID().toString();
  }

  public Comment() {
    contentsHolder = new ContentsHolder();
    commentId = UUID.randomUUID().toString();
  }

  public void setCommentId(String commentId) {
    this.commentId = commentId;
  }

  public String getCommentId() {
    return commentId;
  }

  @Override
  public List<ContentPair> getContentList() {
    return contentsHolder.getContentList();
  }

  @Override
  public void setContentList(List<ContentPair> contentList) {
    contentsHolder.setContentList(contentList);
  }

  @Override
  public boolean addContent(ContentPair contentPair) {
    return contentsHolder.addContent(contentPair);
  }

  @Override
  public boolean removeContent(ContentPair contentPair) {
    return contentsHolder.removeContent(contentPair);
  }

  @Override
  public boolean removeContent(int index) {
    return contentsHolder.removeContent(index);
  }

  @Override
  public int getNumberOfContent() {
    return contentsHolder.getNumberOfContent();
  }
}
