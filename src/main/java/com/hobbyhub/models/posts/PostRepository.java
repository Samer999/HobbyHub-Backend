package com.hobbyhub.models.posts;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {
  Post getPostById(String id);
  List<Post> getPostsByIdInOrderByDateCreated(List <String> postIds);
  List<Post> getPostsByCategoriesHolder_CategoriesIsContainingOrderByDateCreated(List <String> categories);
  List<Post> getPostsByCreatorUsernameInOrderByDateCreated(List <String> usernames);

}
