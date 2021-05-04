package com.hobbyhub.models.hobbies;

import com.hobbyhub.models.posts.Post;
import com.hobbyhub.models.posts.PostRepository;
import com.hobbyhub.models.posts.PostService;
import com.hobbyhub.models.users.UserModel;
import com.hobbyhub.models.users.UserRepository;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.*;
import static org.mockito.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class HobbyServiceTest {

    @InjectMocks
    HobbyService hobbyService;

    @Mock
    HobbyRepository hobbyRepository;

    @Mock
    PostService postService;

    @Test(expected = IllegalArgumentException.class)
    public void addHobby_whenHobbyIdIsNotNull_thenThrowIllegalArgumentException(){
        //arrange
        Hobby hobby=spy(new Hobby());
        when(hobby.getId()).thenReturn("111");

        //act
        hobbyService.addHobby(hobby);

    }

    @Test
    public void addHobby_whenHobbyIdIsNull_thenReturnHobby(){
        //arrange
        Hobby hobby=spy(new Hobby());
        Hobby hobby1;
        when(hobby.getId()).thenReturn(null);
        when(hobbyRepository.save(any(Hobby.class))).thenReturn(hobby);

        //act
        hobby1=hobbyService.addHobby(hobby);

        //assert
        assertEquals(hobby1,hobby);

    }

    @Test(expected = IllegalArgumentException.class)
    public void updateHobby_whenHobbyIdIsNull_thenThrowIllegalArgumentException(){
        //arrange
        Hobby hobby=spy(new Hobby());
        when(hobby.getId()).thenReturn(null);

        //act
        hobbyService.update(hobby);

    }
    @Test
    public void updateHobby_whenHobbyIdIsNotNull_thenReturnHobby(){
        //arrange
        Hobby hobby=spy(new Hobby());
        Hobby hobby1;
        when(hobby.getId()).thenReturn("111");
        when(hobbyRepository.save(any(Hobby.class))).thenReturn(hobby);

        //act
        hobby1=hobbyService.update(hobby);

        //assert
        assertEquals(hobby1,hobby);

    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteHobby_whenHobbyIdIsNull_thenThrowIllegalArgumentException(){
        //arrange
        Hobby hobby=spy(new Hobby());
        when(hobby.getId()).thenReturn(null);

        //act
        hobbyService.deleteHobby(hobby);

    }

    @Test
    public void deleteHobby_whenHobbyIdIsNotNull_thenHobbyIsDeleted(){
        //arrange
        Hobby hobby=spy(new Hobby());
        when(hobby.getId()).thenReturn(null);
        hobbyService.addHobby(hobby);
        when(hobbyRepository.save(any(Hobby.class))).thenReturn(hobby);
        when(hobby.getId()).thenReturn("111");


        //act
        hobbyService.deleteHobby(hobby);

        //assert
        assertEquals(0,hobbyService.getAllHobbies().size());

    }

    @Test
    public void getHobbyByName_whenCalled_thenReturnHobby(){
        //arrange
        Hobby hobby=spy(new Hobby());
        Hobby hobby1;
        when(hobbyRepository.getHobbyByName(anyString())).thenReturn(hobby);


        //act
        hobby1=hobbyService.getHobbyByName("Football");

        //assert
        assertEquals(hobby1,hobby);

    }

    @Test(expected = IllegalArgumentException.class)
    public void getHobbyFeed_whenHobbyNameIsNull_thenThrowIllegalArgumentException(){
        //arrange
        String hobbyName=null;

        //act
        hobbyService.getHobbyFeed(hobbyName);

    }

    @Test
    public void getHobbyFeed_whenCalled_thenReturnPostsWhereHobbyIsTagged(){
        //arrange
        List <Post> list=new ArrayList<>();
        Post post=new Post("Abderraouf",new Date());
        Post post1= new Post("Abderraouf",new Date());
        when(hobbyRepository.getHobbyByName(anyString())).thenReturn(new Hobby());
        postService.newPost(post);
        postService.newPost(post1);

        //act
        list =hobbyService.getHobbyFeed("Football");

        //assert
        assertEquals(list.get(0).getCategories(),"Football");


    }


}
