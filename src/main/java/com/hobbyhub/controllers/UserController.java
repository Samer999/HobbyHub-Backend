package com.hobbyhub.controllers;

import com.hobbyhub.models.users.UpdateUserRequest;
import com.hobbyhub.models.users.UserModel;
import com.hobbyhub.models.users.UserService;
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
  public UserModel updateUser(@RequestBody UpdateUserRequest request) {
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

  private void populateUpdatedUserInformation(UserModel userModel, UpdateUserRequest request) {
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
