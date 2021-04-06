package com.hobbyhub.models.users;

import com.hobbyhub.models.hobbies.HobbyRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  @Autowired private UserRepository userRepository;
  @Autowired private HobbyRepository hobbyRepository;

  @Override
  public UserDetails loadUserByUsername(String usernameToSearch) throws UsernameNotFoundException {
    UserModel userModel = userRepository.getByUsername(usernameToSearch);
    if (userModel == null) return null;
    return new User(userModel.getUsername(), userModel.getPassword(), new ArrayList<>());
  }

  public UserModel newUser(UserModel userModel) {
    if (userModel.getId() != null) {
      throw new IllegalArgumentException(String.format("new userModel must not have id[%s]", userModel.getId()));
    }
    return userRepository.save(userModel);
  }

  public UserModel followUser(UserModel userModel, UserModel userToFollow) {
    if (userModel.getUsername().equals(userToFollow.getUsername())) {
      throw new IllegalArgumentException("users can't follow/unfollow themselves");
    }
    userModel.addUserFollowing(userToFollow.getUsername());
    userToFollow.addFollower(userModel.getUsername());
    updateUser(userToFollow);
    updateUser(userModel);
    return userModel;
  }

  public UserModel unfollowUser(UserModel userModel, UserModel userToFollow) {
    if (userModel.getUsername().equals(userToFollow.getUsername())) {
      throw new IllegalArgumentException("users can't follow/unfollow themselves");
    }
    userModel.removeUserFollowing(userToFollow.getUsername());
    userToFollow.removeFollower(userModel.getUsername());
    updateUser(userToFollow);
    updateUser(userModel);
    return userModel;
  }

  public UserModel followHobby(UserModel userModel, String hobbyName) {
    if (hobbyRepository.getHobbyByName(hobbyName) == null) {
      throw new IllegalArgumentException("No hobby exist with this name " + hobbyName);
    }
    userModel.addHobbyFollowing(hobbyName);
    updateUser(userModel);
    return userModel;
  }

  public UserModel unfollowHobby(UserModel userModel, String hobbyName) {
    if (hobbyRepository.getHobbyByName(hobbyName) == null) {
      throw new IllegalArgumentException("No hobby exist with this name " + hobbyName);
    }
    userModel.removeHobbyFollowing(hobbyName);
    updateUser(userModel);
    return userModel;
  }

  public UserModel getUserModel(String username) {
    return userRepository.getByUsername(username);
  }

  public boolean isNewUsername(String username) {
    return userRepository.getByUsername(username) == null;
  }

  public void updateUser(UserModel userModel) {
    if (userModel.getId() == null) {
      throw new IllegalArgumentException("userModel doesn't have id, can't update it");
    }
    userRepository.save(userModel);
  }
}
