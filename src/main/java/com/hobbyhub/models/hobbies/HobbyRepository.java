package com.hobbyhub.models.hobbies;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HobbyRepository extends MongoRepository<Hobby, String> {
  Hobby getHobbyByName(String name);
  long count();
}
