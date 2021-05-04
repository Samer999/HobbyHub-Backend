package com.hobbyhub.models.users;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {
  UserModel getByUsername(String username);
  List<UserModel> getUserModelByUsernameIn(List<String> usernames);
}
