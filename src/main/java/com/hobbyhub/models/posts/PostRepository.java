package com.hobbyhub.models.posts;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
  Post getPostById(String id);
  List<Post> findTop10BygetPostByLikesHolder_NumberOfLikes();

}
