package com.hobbyhub.controllers;

import com.hobbyhub.models.hobbies.HobbyService;
import com.hobbyhub.models.posts.PostService;
import com.hobbyhub.models.users.UserService;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalyticsController {
  @Autowired PostService postService;
  @Autowired UserService userService;
  @Autowired HobbyService hobbyService;

  @GetMapping(AppUrls.ANALYTICS_POSTS_NUMBER)
  public Long getNumberOfPosts() {
    return postService.getPostsCount();
  }

  @GetMapping(AppUrls.ANALYTICS_POSTS_NUMBER + "/{categoryName}")
  public Long getNumberOfCategoryPosts(@PathVariable String categoryName) {
    return postService.getPostsCountByCategoriesContaining(Collections.singletonList(categoryName));
  }

  @GetMapping(AppUrls.ANALYTICS_USERS_NUMBER)
  public Long getNumberOfUsers() {
    return userService.getUserCount();
  }

  @GetMapping(AppUrls.ANALYTICS_HOBBIES_NUMBER)
  public Long getNumberOfHobbies() {
    return hobbyService.getHobbiesCount();
  }
}
