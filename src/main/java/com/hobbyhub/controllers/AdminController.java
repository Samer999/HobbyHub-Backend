package com.hobbyhub.controllers;


import com.hobbyhub.configuration.SecurityConfiguration;
import com.hobbyhub.models.hobbies.Hobby;
import com.hobbyhub.models.hobbies.HobbyRequest;
import com.hobbyhub.models.hobbies.HobbyService;
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
public class AdminController {
  @Autowired
  private HobbyService hobbyService;

  @GetMapping(AppUrls.ADMIN_HOBBY+ "/{hobbyName}")
  public Hobby getHobby(@PathVariable String hobbyName) {
    checkAdmin();
    return hobbyService.getHobbyByName(hobbyName);
  }

  @PostMapping(AppUrls.ADMIN_HOBBY)
  public Hobby addHobby(@RequestBody HobbyRequest hobbyRequest) {
    checkAdmin();
    Hobby hobby = new Hobby();
    populateHobbyInformation(hobby, hobbyRequest);
    return hobbyService.addHobby(hobby);
  }

  @PutMapping(AppUrls.ADMIN_HOBBY)
  public Hobby updateHobby(@RequestBody HobbyRequest hobbyRequest) {
    checkAdmin();
    Hobby hobby = new Hobby();
    populateHobbyInformation(hobby, hobbyRequest);
    return hobbyService.update(hobby);
  }

  @DeleteMapping(AppUrls.ADMIN_HOBBY + "/{hobbyName}")
  public Hobby deleteHobby(@PathVariable String hobbyName) {
    checkAdmin();
    Hobby hobby = hobbyService.getHobbyByName(hobbyName);
    hobbyService.deleteHobby(hobby);
    return hobby;
  }

  private void populateHobbyInformation(Hobby hobby, HobbyRequest hobbyRequest) {
    hobby.setId(hobbyRequest.getId());
    hobby.setName(hobbyRequest.getName());
    hobby.setDescription(hobbyRequest.getDescription());
    hobby.setImage(hobbyRequest.getImage());
  }

  private void checkAdmin() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    if (!SecurityConfiguration.ADMIN_USERNAME.equals(username)) {
      throw new IllegalArgumentException("forbidden, must be ADMIN");
    }
  }
}
