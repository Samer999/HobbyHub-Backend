package com.hobbyhub.models.users;

import com.google.common.collect.ImmutableList;
import com.hobbyhub.models.hobbies.HobbyRepository;
import com.hobbyhub.models.posts.Post;
import com.hobbyhub.models.posts.PostService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.NonNull;
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

  @Autowired private PostService postService;

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

  public List<UserModel> getFollowingUsers(UserModel userModel) {
    List<String> followingUsersUsernames = userModel.getUsersFollowing();
    return userRepository.getUserModelByUsernameIn(followingUsersUsernames);
  }

  public void updateUserCurrentLocation(UserModel userModel, String location) {
    if (userModel.getId() == null) {
      throw new IllegalArgumentException("userModel doesn't have id, can't update it");
    }
    if (location == null) {
      throw new NullPointerException("location value is null");
    }
    userModel.setCurrentLocation(location);
    userRepository.save(userModel);
  }

  public List<Post> getFeed(UserModel userModel) {
    List<Post> postsByCreatorsUsername = getPostsByCreatorsUsername(userModel.getUsersFollowing());
    List<Post> postsByCategories = getPostsByCategories(userModel.getHobbiesFollowing());
    List<Post> userPosts = getPostsByIds(userModel.getPosts());
    List<List<Post>> postLists = ImmutableList.of(postsByCreatorsUsername, postsByCategories, userPosts);
    return orderPostListByDate(removeDuplicatePosts(postLists));
  }

  public long getUserCount() {
    return userRepository.count();
  }

  public List<UserModel> getUsersList() {
    return userRepository.findAll();
  }

  public void suspendUser(String username) {
    UserModel user = userRepository.getByUsername(username);
    user.setSuspended(true);
    updateUser(user);
  }

  public void unsuspendUser(String username) {
    UserModel user = userRepository.getByUsername(username);
    user.setSuspended(false);
    updateUser(user);
  }

  public boolean isUserSuspended(String username) {
    UserModel user = userRepository.getByUsername(username);
    return user.isSuspended() != null && user.isSuspended();
  }

  private List<Post> getPostsByIds(List<String> postIds) {
    return postService.getPostsByIdIn(postIds);
  }

  private List<Post> getPostsByCreatorsUsername(List<String> usernames) {
    return postService.getPostsByCreatorUsernameIn(usernames);
  }

  private List<Post> getPostsByCategories(List<String> categories) {
    return postService.getPostsByCategoriesContaining(categories);
  }

  private List<Post> removeDuplicatePosts(@NonNull List<List<Post>> postLists) {
    Set<Post> setOfPosts = new HashSet<>();
    for(List<Post> posts : postLists) {
      setOfPosts.addAll(posts);
    }
    return new ArrayList<>(setOfPosts);
  }

  private List<Post> orderPostListByDate(List<Post> posts) {
    posts.sort((post1, post2) -> post2.getDateCreated().compareTo(post1.getDateCreated()));
    return posts;
  }
}
