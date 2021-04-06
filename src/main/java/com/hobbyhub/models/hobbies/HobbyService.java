package com.hobbyhub.models.hobbies;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HobbyService {
  @Autowired
  private HobbyRepository hobbyRepository;

  public Hobby addHobby(Hobby hobby) {
    if (hobby.getId() != null) {
      throw new IllegalArgumentException("hobby got id, can't add it");
    }
    return hobbyRepository.save(hobby);
  }

  public List<Hobby> getAllHobbies() {
    return hobbyRepository.findAll();
  }

  public Hobby update(Hobby hobby) {
    if (hobby.getId() == null) {
      throw new IllegalArgumentException("hobby doesn't have id, can't update it");
    }
    return hobbyRepository.save(hobby);
  }

  public void deleteHobby(Hobby hobby) {
    if (hobby.getId() == null) {
      throw new IllegalArgumentException("hobby doesn't have id, can't delete it");
    }
    hobbyRepository.delete(hobby);
  }

  public Hobby getHobbyByName(String hobbyName) {
    return hobbyRepository.getHobbyByName(hobbyName);
  }
}
