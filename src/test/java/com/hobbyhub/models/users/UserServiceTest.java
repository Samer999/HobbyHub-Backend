package com.hobbyhub.models.users;

import com.hobbyhub.models.hobbies.Hobby;
import com.hobbyhub.models.hobbies.HobbyRepository;
import com.hobbyhub.models.posts.Post;
import com.hobbyhub.models.posts.PostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import static org.mockito.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    HobbyRepository hobbyRepository;

    @Mock
    PostService postService;

    static int number=0;

    public static UserModel getUserModel() {

        UserModel userModel = new UserModel
                (String.format("User "+ ++number), String.format("Name "+ number), "2345678",
                        "abderraouf@gmail.com", "Firo", "Drine",
                        "Male", "home","Football", new ArrayList<String>(), new ArrayList<String>(),
                        new ArrayList<String>(), new ArrayList<String>());

        return userModel;
    }


    @Test
    public void loadUserByUsername_whenUsernameIsNotFound_thenReturnNull() {
        //arrange
        UserDetails userDetails;
        when(userRepository.getByUsername(anyString())).thenReturn(null);

        //act
        userDetails = userService.loadUserByUsername("Abderraouf");

        //assert
        assertNull(userDetails);
    }

     @Test
    public void loadUserByUsername_whenUsernameIsFound_thenReturnUser() {
        //arrange
        UserModel userModel=getUserModel();
        UserDetails userDetails;
        when(userRepository.getByUsername(anyString())).thenReturn(userModel);

        //act
        userDetails=userService.loadUserByUsername("Abderraouf");

        //assert
        assertEquals(userDetails.getUsername(),userModel.getUsername());

    }

    @Test(expected = IllegalArgumentException.class)
    public void newUser_whenUserIdIsNotNull_thenThrowIllegalArgumentException(){
        //arrange
        UserModel userModel=getUserModel();

        //act
        userService.newUser(userModel);

    }

    @Test
    public void newUser_whenUserIdIsNull_thenReturnUserModel(){
        //arrange
        UserModel userModel=spy(getUserModel());
        UserModel userModel1;
        when(userRepository.getByUsername(anyString())).thenReturn(userModel);
        when(userModel.getId()).thenReturn(null);
        when(userRepository.save(any(UserModel.class))).thenReturn(userModel);

        //act
        userModel1=userService.newUser(userModel);

        //assert
        assertEquals(userModel,userModel1);


    }

    @Test(expected = IllegalArgumentException.class)
    public void updateUser_whenUserIdIsNull_thenThrowIllegalArgumentException(){
        //arrange
        UserModel userModel=spy(getUserModel());
        when(userModel.getId()).thenReturn(null);

        //act
        userService.updateUser(userModel);
    }

    @Test(expected = IllegalArgumentException.class)
    public void followUser_whenUserModelUserNameEqualsFollowedUserModelUsername_thenThrowIllegalArgumentException(){
        //arrange
        UserModel userModel= getUserModel();
        UserModel userModel1= getUserModel();

        //act
        userService.followUser(userModel,userModel1);

    }

    @Test
    public void followUser_whenUserModelUserNameNotEqualsFollowedUserModelUsername_thenReturnUserModel(){
        //arrange
        UserModel userModel= getUserModel();
        UserModel userModel1= getUserModel();
        UserModel userModel2;
        userModel1.setUsername("Mohamed");


        //act
        userModel2=userService.followUser(userModel,userModel1);

        //assert
        assertEquals(userModel,userModel2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void unfollowUser_whenUserModelUserNameEqualsFollowedUserModelUsername_thenThrowIllegalArgumentException(){
        //arrange
        UserModel userModel= getUserModel();
        UserModel userModel1= getUserModel();

        //act
        userService.unfollowUser(userModel,userModel1);

    }

    @Test
    public void unfollowUser_whenUserModelUserNameNotEqualsFollowedUserModelUsername_thenReturnUserModel(){
        //arrange
        UserModel userModel= getUserModel();
        UserModel userModel1= getUserModel();
        userModel1.setUsername("Mohamed");
        UserModel userModel2;
        userService.followUser(userModel,userModel1);



        //act
        userModel2=userService.followUser(userModel,userModel1);

        //assert
        assertEquals(userModel,userModel2);

    }

    @Test(expected = IllegalArgumentException.class)
    public void followHobby_whenHobbyNotExistInRepository_thenThrowIllegalArgumentException(){
        //arrange
        UserModel userModel= getUserModel();
        when(hobbyRepository.getHobbyByName(anyString())).thenReturn(null);

        //act
        userService.followHobby(userModel,"Swimming");

    }

    @Test
    public void followHobby_whenHobbyExistInRepository_thenReturnUserModel(){
        //arrange
        UserModel userModel= getUserModel();
        UserModel userModel1;
        when(hobbyRepository.getHobbyByName(anyString())).thenReturn(new Hobby());

        //act
        userModel1 = userService.followHobby(userModel,"Swimming");

        //assert
        assertEquals(userModel,userModel1);

    }

    @Test(expected = IllegalArgumentException.class)
    public void unfollowHobby_whenHobbyNotExistInRepository_thenThrowIllegalArgumentException(){
        //arrange
        UserModel userModel= getUserModel();
        when(hobbyRepository.getHobbyByName(anyString())).thenReturn(null);

        //act
        userService.unfollowHobby(userModel,"Swimming");

    }

    @Test
    public void unfollowHobby_whenHobbyExistInRepository_thenReturnUserModel(){
        //arrange
        UserModel userModel= getUserModel();
        UserModel userModel1;
        when(hobbyRepository.getHobbyByName(anyString())).thenReturn(new Hobby());
        userService.followHobby(userModel,"Swimming");

        //act
        userModel1 = userService.unfollowHobby(userModel,"Swimming");

        //assert
        assertEquals(userModel,userModel1);

    }

    @Test
    public void getFollowingUsers_whenCalled_thenReturnListOfFollowedUsers(){
        //arrange
        UserModel userModel=getUserModel();
        UserModel userModel1=getUserModel();
        userRepository.save(userModel);
        userRepository.save(userModel1);
        userModel.addUserFollowing(userModel1.getUsername());
        userModel1.addFollower(userModel.getUsername());
        List<UserModel> list=new ArrayList<>();

        //act
        list=userService.getFollowingUsers(userModel);

        //assert
        assertEquals(list.get(0).getUsername(),userModel1.getUsername());

    }

    @Test(expected = IllegalArgumentException.class)
    public void updateUserCurrentLocation_whenUserModelIdIsNull_thenThrowIllegalArgumentException(){
        //arrange
        UserModel userModel=spy(getUserModel());
        when(userModel.getId()).thenReturn(null);

        //act
        userService.updateUserCurrentLocation(userModel,"Home");
    }

    @Test(expected = NullPointerException.class)
    public void updateUserCurrentLocation_whenLocationIsNull_thenThrowNullPointerException(){
        //arrange
        UserModel userModel=spy(getUserModel());
        String location=null;

        //act
        userService.updateUserCurrentLocation(userModel,location);
    }

    @Test
    public void updateUserCurrentLocation_whenLocationIsNotNullAndUserModelIdIsNotNull_thenLocationIsUpdated(){
        //arrange
        UserModel userModel=spy(getUserModel());
        String location="Amman";

        //act
        userService.updateUserCurrentLocation(userModel,location);

        //assert
        assertEquals(userModel.getCurrentLocation(),location);
    }




    @Test
    public void getFeed_whenCalled_thenReturnPostsFromFollowedUsersAndFollowedPosts(){
        //arrange
        UserModel userModel= getUserModel();
        UserModel userModelToFollow=getUserModel();
        userModelToFollow.setUsername("Samer");
        userModelToFollow.addPost("Football");
        Post post= new Post("Samer",new Date());
        Post post1= new Post("Samer",new Date());
        post.addCategoryTag("Football");
        post1.addCategoryTag("Rugby");
        Hobby hobby=new Hobby();
        hobby.setName("Football");
        List <Post> list=new ArrayList<>();
        list.add(post);
        list.add(post1);
        List <Post> list1= new ArrayList<>();
        list1.add(post);
        when(hobbyRepository.getHobbyByName("Football")).thenReturn(hobby);
        userService.followUser(userModel,userModelToFollow);
        userService.followHobby(userModel,"Football");
        when(postService.getPostsByCreatorUsernameIn(userModel.getUsersFollowing())).thenReturn(list);
        when(postService.getPostsByCategoriesContaining(userModel.getHobbiesFollowing())).thenReturn(list1);

        //act
        List<Post> finalList=userService.getFeed(userModel);
        for(Post l: finalList)
            System.out.println(l.getCategories());

        //assert
        assertEquals(2,finalList.size());


    }




}

