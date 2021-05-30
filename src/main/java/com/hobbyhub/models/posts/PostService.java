package com.hobbyhub.models.posts;

import com.hobbyhub.models.comments.Comment;
import com.hobbyhub.models.likes.Like;
import com.hobbyhub.models.users.UserModel;
import com.hobbyhub.models.users.UserRepository;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

  @Autowired private PostRepository postRepository;

  @Autowired private UserRepository userRepository;

  public Post newPost(Post post) {
    if (post.getId() != null) {
      throw new IllegalArgumentException(String.format("new post must not have id[%s]", post.getId()));
    }
    post = postRepository.save(post);
    UserModel userModel = userRepository.getByUsername(post.getCreatorUsername());
    userModel.addPost(post.getId());
    userRepository.save(userModel);
    return post;
  }

  public Post update(Post post) {
    if (post.getId() == null) {
      throw new IllegalArgumentException("post doesn't have id, can't update it");
    }
    return postRepository.save(post);
  }

  public void deletePost(Post post, @NonNull String username) {
    if (!username.equals(post.getCreatorUsername())) {
      throw new IllegalArgumentException(String.format("post creatorUsername[%s], doesn't equal the entered "
          + "username[%s]", post.getCreatorUsername(), username));
    }
    postRepository.delete(post);
    UserModel userModel = userRepository.getByUsername(post.getCreatorUsername());
    userModel.removePost(post.getId());
    userRepository.save(userModel);
  }

  public Post getPostById(@NonNull String id) {
    return postRepository.getPostById(id);
  }

  public List<Post> getPostsByCategoriesContaining(List <String> categories) {
    return postRepository.getPostsByCategoriesHolder_CategoriesIsContainingOrderByDateCreated(categories);
  }

  public List<Post> getPostsByCreatorUsernameIn(List <String> usernames) {
    return postRepository.getPostsByCreatorUsernameInOrderByDateCreated(usernames);
  }

  public List<Post> getPostsByIdIn(List <String> postIds) {
    return postRepository.getPostsByIdInOrderByDateCreated(postIds);
  }

  public void likePost(Post post, @NonNull String username) {
    if (post.likeIdExists(username)) {
      throw new IllegalArgumentException("post is already liked");
    }
    post.addLike(new Like(username, new Date()));
    update(post);
  }

  public void unlikePost(Post post, @NonNull String username) {
    if (!post.likeIdExists(username)) {
      throw new IllegalArgumentException("post is not liked");
    }
    post.removeLike(username);
    update(post);
  }

  public void addComment(Post post, Comment comment) {
    post.addComment(comment);
    update(post);
  }

  public void removeComment(Post post, @NonNull String commentId, @NonNull String username) {
    Comment comment = post.getCommentById(commentId);
    if (!comment.getCreatorUsername().equals(username)) {
      throw new IllegalArgumentException(
          String.format(
              "comment creatorUsername[%s] doesn't equal caller username[%s]",
              comment.getCreatorUsername(), username));
    }
    post.removeComment(commentId);
    update(post);
  }

  public List<Post> getTrending() {
    final long DAY_IN_MS = 86400000; // 1000 * 60 * 60 * 24
    Date date7daysAgo = new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));
    List<Post> posts = postRepository.findAll();
    return posts.stream().filter(post -> post.getDateCreated().after(date7daysAgo))
        .sorted((Comparator.comparingInt(Post::getNumberOfLikes))).limit(10).collect(Collectors.toList());
  }

  public long getPostsCount() {
    return postRepository.count();
  }

  public long getPostsCountByCategoriesContaining(List <String> categories) {
    return getPostsByCategoriesContaining(categories).size();
  }

  public List<Post> getAllPosts() {
    return postRepository.findAll();
  }
}
