package com.hobbyhub.controllers;


import com.hobbyhub.configuration.SecurityConfiguration;
import com.hobbyhub.models.comments.Comment;
import com.hobbyhub.models.hobbies.Hobby;
import com.hobbyhub.models.hobbies.HobbyRequest;
import com.hobbyhub.models.hobbies.HobbyService;
import com.hobbyhub.models.posts.Post;
import com.hobbyhub.models.posts.PostService;
import com.hobbyhub.models.users.UserModel;
import com.hobbyhub.models.users.UserRequest;
import com.hobbyhub.models.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
  @Autowired private HobbyService hobbyService;
  @Autowired private PostService postService;
  @Autowired private UserService userService;


  @PostMapping(AppUrls.ADMIN_HOBBY)
  public Hobby addHobby(@RequestBody HobbyRequest hobbyRequest) {
    checkAdmin();
    Hobby hobby = new Hobby();
    populateHobbyInformation(hobby, hobbyRequest);
    return hobbyService.addHobby(hobby);
  }

  @PutMapping(AppUrls.ADMIN_HOBBY)
  public Hobby updateHobby(@RequestBody HobbyRequest hobbyRequest) {
    checkAdmin();
    Hobby hobby = new Hobby();
    populateHobbyInformation(hobby, hobbyRequest);
    return hobbyService.update(hobby);
  }

  @DeleteMapping(AppUrls.ADMIN_HOBBY + "/{hobbyName}")
  public Hobby deleteHobby(@PathVariable String hobbyName) {
    checkAdmin();
    Hobby hobby = hobbyService.getHobbyByName(hobbyName);
    hobbyService.deleteHobby(hobby);
    return hobby;
  }

  @DeleteMapping(AppUrls.ADMIN_POST + "/{postId}")
  public Post deletePost(@PathVariable String postId) {
    checkAdmin();
    Post post = postService.getPostById(postId);
    postService.deletePost(post, post.getCreatorUsername());
    return post;
  }

  @DeleteMapping(AppUrls.ADMIN_COMMENT + "/{postId}/{commentId}")
  public Post deleteComment(@PathVariable String postId, @PathVariable String commentId) {
    checkAdmin();
    Post post = postService.getPostById(postId);
    Comment comment = post.getCommentById(commentId);
    String username = comment.getCreatorUsername();
    postService.removeComment(post, commentId, username);
    return post;
  }

  @PutMapping(AppUrls.ADMIN_USER_SUSPEND)
  public UserModel suspendUser(@RequestBody UserRequest userRequest) {
    String username = userRequest.getUsername();
    userService.suspendUser(username);
    return userService.getUserModel(username);
  }

  @PutMapping(AppUrls.ADMIN_USER_UNSUSPEND)
  public UserModel unsuspendUser(@RequestBody UserRequest userRequest) {
    String username = userRequest.getUsername();
    userService.unsuspendUser(username);
    return userService.getUserModel(username);
  }

  private void populateHobbyInformation(Hobby hobby, HobbyRequest hobbyRequest) {
    hobby.setId(hobbyRequest.getId());
    hobby.setName(hobbyRequest.getName());
    hobby.setDescription(hobbyRequest.getDescription());
    hobby.setImage(hobbyRequest.getImage());
  }

  private void checkAdmin() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    if (!SecurityConfiguration.ADMIN_USERNAME.equals(username)) {
      throw new IllegalArgumentException("forbidden, must be ADMIN");
    }
  }
}
