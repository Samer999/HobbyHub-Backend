package com.hobbyhub.models.users;

import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Builder
public class UserModel {

  @Id private String id;
  @NonNull private String username;
  @NonNull private String password;
  @NonNull private String email;
  @NonNull private String firstName;
  @NonNull private String lastName;
  @NonNull private String gender;
  private String currentLocation;
  private String imageUrl;
  private List<String> usersFollowing;
  private List<String> followers;
  private List<String> hobbiesFollowing;
  private List<String> posts;
  private Boolean isSuspended;

  public String getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getCurrentLocation() {
    return currentLocation;
  }

  public void setCurrentLocation(String currentLocation) {
    this.currentLocation = currentLocation;
  }

  public List<String> getUsersFollowing() {
    return usersFollowing;
  }

  public void setUsersFollowing(List<String> usersFollowing) {
    this.usersFollowing = usersFollowing;
  }

  public List<String> getFollowers() {
    return followers;
  }

  public void setFollowers(List<String> followers) {
    this.followers = followers;
  }

  public List<String> getHobbiesFollowing() {
    return hobbiesFollowing;
  }

  public void setHobbiesFollowing(List<String> hobbiesFollowing) {
    this.hobbiesFollowing = hobbiesFollowing;
  }

  public List<String> getPosts() {
    return posts;
  }

  public void setPosts(List<String> posts) {
    this.posts = posts;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  public boolean addUserFollowing (String userFollowing) {
    if (usersFollowing.contains(userFollowing)) {
      return false;
    }
    return usersFollowing.add(userFollowing);
  }

  public boolean removeUserFollowing(String userFollowing) {
    return usersFollowing.remove(userFollowing);
  }

  public boolean addFollower(String follower) {
    if (followers.contains(follower)) {
      return false;
    }
    return followers.add(follower);
  }

  public boolean removeFollower(String follower) {
    return followers.remove(follower);
  }

  public boolean addHobbyFollowing(String hobbyFollowing) {
    if (hobbiesFollowing.contains(hobbyFollowing)) {
      return false;
    }
    return hobbiesFollowing.add(hobbyFollowing);
  }

  public boolean removeHobbyFollowing (String hobbyFollowing) {
    return hobbiesFollowing.remove(hobbyFollowing);
  }

  public boolean addPost(String postId) {
    if (posts.contains(postId)) {
      return false;
    }
    return posts.add(postId);
  }

  public boolean removePost(String postId) {
    return posts.remove(postId);
  }

  public Boolean isSuspended() {
    return isSuspended;
  }

  public void setSuspended(Boolean suspended) {
    isSuspended = suspended;
  }
}
