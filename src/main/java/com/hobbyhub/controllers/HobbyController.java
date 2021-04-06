package com.hobbyhub.controllers;

import com.hobbyhub.models.hobbies.Hobby;
import com.hobbyhub.models.hobbies.HobbyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HobbyController {
  @Autowired HobbyService hobbyService;

  @GetMapping(AppUrls.GET_ALL_HOBBIES)
  public List<Hobby> getAllHobbies() {
    return hobbyService.getAllHobbies();
  }
}
