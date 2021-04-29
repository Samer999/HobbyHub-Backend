package com.hobbyhub.models.users;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {
  UserModel getByUsername(String username);
  List<UserModel> getUserModelByUsernameIn(List<String> usernames);
}
