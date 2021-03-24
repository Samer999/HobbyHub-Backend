package com.hobbyhub.models.comments;

import java.util.ArrayList;
import java.util.List;


public class CommentsHolder implements Commentable {

  private List<Comment> comments;

  public CommentsHolder() {
    this.comments = new ArrayList<>();
  }

  @Override
  public List<Comment> getComments() {
    return comments;
  }

  @Override
  public void removeComment(String commentId) {
    Comment comment = getCommentById(commentId);
    comments.remove(comment);
  }

  @Override
  public void addComment(Comment comment) {
    if (comment == null) {
      throw new NullPointerException("comment value is null");
    }
    if (commentIdExists(comment.getCommentId())) {
      throw new IllegalArgumentException(
          String.format("comment with this id[%s] exist already", comment.getCommentId()));
    }
    comments.add(comment);
  }

  @Override
  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }

  @Override
  public boolean commentIdExists(String commentId) {
    if (commentId == null) {
      throw new NullPointerException("commentId is null");
    }
    for (Comment comment : comments) {
      if (comment.getCommentId().equals(commentId)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Comment getCommentById(String commentId) {
    if (commentIdExists(commentId)) {
      for (Comment comment : comments) {
        if (comment.getCommentId().equals(commentId)) {
          return comment;
        }
      }
    }
    // this will be executed if no comment is found with passed commentId
    throw new IllegalArgumentException("no comment found with this id: " + commentId);
  }

  @Override
  public int getNumberOfComments() {
    return comments.size();
  }
}
