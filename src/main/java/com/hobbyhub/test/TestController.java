package com.hobbyhub.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
  @Autowired
  private DataContextRepository dataContextRepository;
  @PostMapping("/test")
  public DataContext test(@RequestBody DataContext dataContext) {
    return dataContextRepository.save(dataContext);
  }

  @PostMapping("/test-get")
  public DataContext testGet(@RequestBody DataContext dataContext) {
    return dataContextRepository.getById(dataContext.getId());
  }

}
