package com.hobbyhub.models.posts;

import com.hobbyhub.models.categories.CategoriesHolder;
import com.hobbyhub.models.categories.Categorizable;
import com.hobbyhub.models.comments.Comment;
import com.hobbyhub.models.comments.Commentable;
import com.hobbyhub.models.comments.CommentsHolder;
import com.hobbyhub.models.contents.ContentPair;
import com.hobbyhub.models.contents.ContentsHolder;
import com.hobbyhub.models.contents.IContentsHolder;
import com.hobbyhub.models.likes.Like;
import com.hobbyhub.models.likes.Likeable;
import com.hobbyhub.models.likes.LikesHolder;
import com.hobbyhub.models.users.UserCreation;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "posts")
public class Post extends UserCreation
    implements Commentable, IContentsHolder, Likeable, Categorizable {

  @Id private String id;
  private CommentsHolder commentsHolder;
  private ContentsHolder contentsHolder;
  private LikesHolder likesHolder;
  private CategoriesHolder categoriesHolder;

  public Post(String creatorUsername, Date dateCreated) {
    super(creatorUsername, dateCreated);
    commentsHolder = new CommentsHolder();
    contentsHolder = new ContentsHolder();
    likesHolder = new LikesHolder();
    categoriesHolder = new CategoriesHolder();
  }

  public String getId() {
    return id;
  }

  @Override
  public List<Comment> getComments() {
    return commentsHolder.getComments();
  }

  @Override
  public void removeComment(String commentId) {
    commentsHolder.removeComment(commentId);
  }

  @Override
  public void addComment(Comment comment) {
    commentsHolder.addComment(comment);
  }

  @Override
  public void setComments(List<Comment> comments) {
    commentsHolder.setComments(comments);
  }

  @Override
  public boolean commentIdExists(String commentId) {
    return commentsHolder.commentIdExists(commentId);
  }

  @Override
  public Comment getCommentById(String commentId) {
    return commentsHolder.getCommentById(commentId);
  }

  @Override
  public int getNumberOfComments() {
    return commentsHolder.getNumberOfComments();
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

  @Override
  public List<Like> getLikes() {
    return likesHolder.getLikes();
  }

  @Override
  public void removeLike(String likeId) {
    likesHolder.removeLike(likeId);
  }

  @Override
  public void addLike(Like like) {
    likesHolder.addLike(like);
  }

  @Override
  public void setLikes(List<Like> likes) {
    likesHolder.setLikes(likes);
  }

  @Override
  public boolean likeIdExists(String likeId) {
    return likesHolder.likeIdExists(likeId);
  }

  @Override
  public Like getLikeById(String likeId) {
    return likesHolder.getLikeById(likeId);
  }

  @Override
  public int getNumberOfLikes() {
    return likesHolder.getNumberOfLikes();
  }

  @Override
  public Set<String> getCategories() {
    return categoriesHolder.getCategories();
  }

  @Override
  public void setCategories(Set<String> categories) {
    categoriesHolder.setCategories(categories);
  }

  @Override
  public boolean removeCategoryTag(String tag) {
    return categoriesHolder.removeCategoryTag(tag);
  }

  @Override
  public boolean addCategoryTag(String tag) {
    return categoriesHolder.addCategoryTag(tag);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    Post post = (Post) o;
    return Objects.equals(id, post.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), id);
  }
}
