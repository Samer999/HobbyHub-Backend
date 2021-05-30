package com.hobbyhub.controllers;


public class AppUrls {

  public static final String SIGN_IN = "/sign-in";
  public static final String SIGN_UP = "/sign-up";
  public static final String AUTHENTICATE_CLIENT = "/authenticate-client";

  public static final String POST = "/post";
  public static final String POST_LIKE = "/post/like";
  public static final String POST_COMMENT = "/post/comment";
  public static final String POST_TRENDING = "/post/trending";

  public static final String USER = "/user";
  public static final String USER_ALL = "/users/all";
  public static final String USER_FEED = "/user/feed";
  public static final String USER_LOCATION = "/user/location";
  public static final String USER_FOLLOWING = "/user/following";
  public static final String USER_FOLLOW_USER = "/user/follow-user";
  public static final String USER_UNFOLLOW_USER = "/user/unfollow-user";
  public static final String USER_FOLLOW_HOBBY = "/user/follow-hobby";
  public static final String USER_UNFOLLOW_HOBBY = "/user/unfollow-hobby";

  public static final String HOBBY = "/hobby";
  public static final String GET_ALL_HOBBIES = "/hobby/all";

  public static final String ADMIN_HOBBY = "/admin/hobby";
  public static final String ADMIN_POST = "/admin/post";
  public static final String ADMIN_ALL_POST = "/admin/post/all";
  public static final String ADMIN_COMMENT = "/admin/post/comment";
  public static final String ADMIN_USER_SUSPEND = "/admin/user/suspend";
  public static final String ADMIN_USER_UNSUSPEND = "/admin/user/unsuspend";

  public static final String ANALYTICS_POSTS_NUMBER = "/analytics/posts-number";
  public static final String ANALYTICS_USERS_NUMBER = "/analytics/users-number";
  public static final String ANALYTICS_HOBBIES_NUMBER = "/analytics/hobbies-number";

  private AppUrls(){
    // no-op
  }
}
