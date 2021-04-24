package com.hobbyhub.controllers;

import com.hobbyhub.models.comments.Comment;
import com.hobbyhub.models.comments.CommentRequest;
import com.hobbyhub.models.posts.Post;
import com.hobbyhub.models.posts.PostRequest;
import com.hobbyhub.models.posts.PostService;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

  @Autowired private PostService postService;

  @GetMapping(AppUrls.POST + "/{postId}")
  public Post getPost(@PathVariable String postId) {
    return postService.getPostById(postId);
  }

  @PostMapping(AppUrls.POST)
  public Post createPost(@RequestBody PostRequest postRequest) {
    Post newPost = new Post(SecurityContextHolder.getContext().getAuthentication().getName(), new Date());
    populatePostInformation(newPost, postRequest);
    return postService.newPost(newPost);
  }

  @PutMapping(AppUrls.POST)
  public Post updatePost(@RequestBody PostRequest postRequest) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Post post = postService.getPostById(postRequest.getId());
    if (post == null) {
      throw new NullPointerException("couldn't find a post with this id " + postRequest.getId());
    }
    if (!isCreator(post, username)) {
      throw new IllegalArgumentException(String.format("the post creatorUsername[%s] is different than the current "
          + "SecurityContext[%s]", post.getCreatorUsername(), username));
    }
    populatePostInformation(post, postRequest);
    return postService.update(post);
  }

  @DeleteMapping(AppUrls.POST + "/{postId}")
  public Post deletePost(@PathVariable String postId) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Post post = postService.getPostById(postId);
    try {
      postService.deletePost(post, username);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return post;
  }


  @PostMapping(AppUrls.POST_LIKE)
  public Post likePost(@RequestBody PostRequest postRequest) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Post post = postService.getPostById(postRequest.getId());
    try {
      postService.likePost(post, username);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }
    return post;
  }

  @DeleteMapping(AppUrls.POST_LIKE + "/{postId}")
  public Post unlikePost(@PathVariable String postId) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Post post = postService.getPostById(postId);
    try {
      postService.unlikePost(post, username);
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    }
    return post;
  }

  @PostMapping(AppUrls.POST_COMMENT)
  public Post addComment(@RequestBody CommentRequest commentRequest) {
    commentRequest.setCreatorUsername(SecurityContextHolder.getContext().getAuthentication().getName());
    Post post = postService.getPostById(commentRequest.getPostId());
    Comment comment = new Comment();
    populateCommentInformation(comment, commentRequest);
    try {
      postService.addComment(post, comment);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return post;
  }

  @DeleteMapping(AppUrls.POST_COMMENT + "/{postId}/{commentId}")
  public Post removeComment(@PathVariable String postId, @PathVariable String commentId) {
    Post post = postService.getPostById(postId);
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    try {
      postService.removeComment(post, commentId, username);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return post;
  }

  @GetMapping(AppUrls.POST_TRENDING)
  public List<Post> getTrending() {
    return postService.getTrending();
  }

  private void populateCommentInformation(Comment comment, CommentRequest commentRequest) {
    if (commentRequest.getCommentId() != null) {
      comment.setCommentId(commentRequest.getCommentId());

    }
    comment.setContentList(commentRequest.getContentList());
    comment.setCreatorUsername(commentRequest.getCreatorUsername());
    comment.setDateCreated(new Date());
  }


  private void populatePostInformation(Post post, PostRequest postRequest) {
    if (postRequest.getContentList() != null) {
      post.setContentList(postRequest.getContentList());
    } else {
      post.setContentList(Collections.emptyList());
    }
    if (postRequest.getCategories() != null) {
      post.setCategories(new HashSet<>(postRequest.getCategories()));
    } else {
      post.setCategories(Collections.emptySet());
    }
  }

  private boolean isCreator(Post post, String username) {
    return post.getCreatorUsername().equals(username);
  }
}
