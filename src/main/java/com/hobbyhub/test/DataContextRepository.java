package com.hobbyhub.test;

import com.hobbyhub.models.users.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataContextRepository extends MongoRepository<DataContext, String> {
  DataContext getById(String id);
}
