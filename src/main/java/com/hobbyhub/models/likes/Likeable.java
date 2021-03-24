package com.hobbyhub.models.likes;

import java.util.List;

public interface Likeable {

  List<Like> getLikes();

  void removeLike(String likeId);

  void addLike(Like like);

  void setLikes(List<Like> likes);

  boolean likeIdExists(String likeId);

  Like getLikeById(String likeId);

  int getNumberOfLikes();
}
