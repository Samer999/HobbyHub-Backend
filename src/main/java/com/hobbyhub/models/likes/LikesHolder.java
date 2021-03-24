package com.hobbyhub.models.likes;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class LikesHolder implements Likeable {

  private List<Like> likes;

  public LikesHolder() {
    likes = new ArrayList<>();
  }

  /** @return a deep copied list of likes */
  @Override
  public List<Like> getLikes() {
    List<Like> copiedList = new ArrayList<>();

    for (Like like : likes) {
      copiedList.add(like);
    }
    return copiedList;
  }

  @Override
  public void removeLike(String likeId) {
    Like like = getLikeById(likeId);
    likes.remove(like);
  }

  @Override
  public void addLike(Like like) {
    if (like == null) {
      throw new NullPointerException("like value is null");
    }
    if (likeIdExists(like.getLikeId())) {
      throw new IllegalArgumentException(
          String.format("an already like with this id[%s] exist", like.getLikeId()));
    }
    likes.add(like);
  }

  @Override
  public boolean likeIdExists(String likeId) {
    if (likeId == null) {
      throw new NullPointerException("likeId is null");
    }
    for (Like like : likes) {
      if (like.getLikeId().equals(likeId)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Like getLikeById(String likeId) {
    if (likeIdExists(likeId)) {
      for (Like like : likes) {
        if (like.getLikeId().equals(likeId)) {
          return like;
        }
      }
    }
    // this will be executed if no like is found with passed likeId
    throw new IllegalArgumentException("no like found with this id: " + likeId);
  }

  @Override
  public int getNumberOfLikes() {
    return likes.size();
  }
}
