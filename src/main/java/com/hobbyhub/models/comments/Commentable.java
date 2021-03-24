package com.hobbyhub.models.comments;

import java.util.List;

public interface Commentable {

  List<Comment> getComments();

  void removeComment(String commentId);

  void addComment(Comment comment);

  void setComments(List<Comment> comments);

  boolean commentIdExists(String commentId);

  Comment getCommentById(String commentId);

  int getNumberOfComments();
}
