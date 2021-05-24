package com.hobbyhub.models.hobbies;

import com.hobbyhub.models.posts.Post;
import com.hobbyhub.models.posts.PostService;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HobbyService {
  @Autowired private HobbyRepository hobbyRepository;

  @Autowired private PostService postService;

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

  public List<Post> getHobbyFeed(String hobbyName) {
    if (getHobbyByName(hobbyName) == null) {
      throw new IllegalArgumentException(String.format("hobbyName[%s] doesn't exist", hobbyName));
    }
    return postService.getPostsByCategoriesContaining(Collections.singletonList(hobbyName));
  }

  public long getHobbiesCount() {
    return hobbyRepository.count();
  }
}
