package com.hobbyhub.controllers;

import com.hobbyhub.models.hobbies.HobbyRequest;
import com.hobbyhub.models.posts.Post;
import com.hobbyhub.models.users.UserRequest;
import com.hobbyhub.models.users.UserModel;
import com.hobbyhub.models.users.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
  @Autowired private UserService userService;

  @GetMapping(AppUrls.USER + "/{username}")
  public UserModel getUser(@PathVariable String username) {
    return userService.getUserModel(username);
  }

  @PutMapping(AppUrls.USER)
  public UserModel updateUser(@RequestBody UserRequest request) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    UserModel userModel = userService.getUserModel(username);
    populateUpdatedUserInformation(userModel, request);
    try {
      userService.updateUser(userModel);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return userModel;
  }

  @PutMapping(AppUrls.USER_FOLLOW_USER)
  public UserModel followUser(@RequestBody UserRequest userRequest) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    UserModel userModel = userService.getUserModel(username);
    UserModel userToFollow = userService.getUserModel(userRequest.getUsername());
    return userService.followUser(userModel, userToFollow);
  }

  @PutMapping(AppUrls.USER_UNFOLLOW_USER)
  public UserModel unfollowUser(@RequestBody UserRequest userRequest) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    UserModel userModel = userService.getUserModel(username);
    UserModel userToFollow = userService.getUserModel(userRequest.getUsername());
    return userService.unfollowUser(userModel, userToFollow);
  }
  
  @PutMapping(AppUrls.USER_FOLLOW_HOBBY)
  public UserModel followHobby(@RequestBody HobbyRequest hobbyRequest) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    UserModel userModel = userService.getUserModel(username);
    return userService.followHobby(userModel, hobbyRequest.getName());
  }

  @PutMapping(AppUrls.USER_UNFOLLOW_HOBBY)
  public UserModel unfollowHobby(@RequestBody HobbyRequest hobbyRequest) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    UserModel userModel = userService.getUserModel(username);
    return userService.unfollowHobby(userModel, hobbyRequest.getName());
  }

  @GetMapping(AppUrls.USER_FEED)
  public List<Post> getFeed() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    UserModel userModel = userService.getUserModel(username);
    return userService.getFeed(userModel);
  }

  @GetMapping(AppUrls.USER_LOCATION)
  public String getCurrentLocation() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    UserModel userModel = userService.getUserModel(username);
    return userModel.getCurrentLocation();
  }

  @PutMapping(AppUrls.USER_LOCATION)
  public UserModel updateCurrentLocation(@RequestBody String location) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    UserModel userModel = userService.getUserModel(username);
    userService.updateUserCurrentLocation(userModel, location);
    return userModel;
  }

  @GetMapping(AppUrls.USER_FOLLOWING)
  public List<UserModel> getFollowingUsers() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    UserModel userModel = userService.getUserModel(username);
    return userService.getFollowingUsers(userModel);
  }

  private void populateUpdatedUserInformation(UserModel userModel, UserRequest request) {
    String firstName = request.getFirstName();
    String lastName = request.getLastName();
    String email = request.getEmail();
    String password = request.getPassword();
    String gender = request.getGender();
    String imageUrl = request.getImageUrl();
    if (firstName != null) {
      userModel.setFirstName(firstName);
    }
    if (lastName != null) {
      userModel.setLastName(lastName);
    }
    if (email != null) {
      userModel.setEmail(email);
    }
    if (password != null) {
      userModel.setPassword(password);
    }
    if (gender != null) {
      userModel.setGender(gender);
    }
    userModel.setImageUrl(imageUrl);
  }
}
