package com.hobbyhub.models.posts;

import com.hobbyhub.models.comments.Comment;
import com.hobbyhub.models.likes.Like;
import com.hobbyhub.models.users.UserModel;
import com.hobbyhub.models.users.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.*;
import static org.mockito.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {

    @InjectMocks
    PostService postService;


    @Mock
    PostRepository postRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    UserModel userModel;

    public static List <Post> getPosts(){
        List <Post> list= new ArrayList<>();
        int number=0;
        int idNumber=0;
        for (int i=0;i<11;i++){
            Post post=new Post(String.format("User" + ++number),new Date());
            list.add(post);

        }
        for(int i=0;i<3;i++){
            for(int j=0;j<10;j++) {
                Like like = new Like(String.format("likeUser" + ++idNumber), new Date());
                list.get(i).addLike(like);
            }
        }
        for(int i=3;i<7;i++){
            for(int j=0;j<7;j++) {
                Like like = new Like(String.format("likeUser" + ++idNumber), new Date());
                list.get(i).addLike(like);
            }
        }
        for(int i=7;i<10;i++){
            for(int j=0;j<5;j++) {
                Like like = new Like(String.format("likeUser" + ++idNumber), new Date());
                list.get(i).addLike(like);
            }
        }
        list.get(10).addLike(new Like(String.format("likeUser" + ++idNumber), new Date()));



        return list;


    }





    @Test(expected = IllegalArgumentException.class)
    public void newPost_whenPostIdIsNotNull_thenThrowIllegalArgumentException(){
        //arrange
        Post post= spy(new Post("Abderraouf",new Date()));
        Post post1;
        when(post.getId()).thenReturn("222");


        //act
        post1=postService.newPost(post);

    }

    @Test
    public void newPost_whenPostIdIsNull_thenReturnPost(){
        //arrange
        Post post= new Post("Abderraouf",new Date());
        Post post1;
        when(postRepository.save((any(Post.class)))).thenReturn(post);
        when(userRepository.getByUsername(anyString())).thenReturn(userModel);



        //act
        post1=postService.newPost(post);

        //assert
        assertEquals(post, post1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void update_whenPostIdIsNull_thenThrowIllegalArgumentException(){
        //arrange
        Post post=spy(new Post("Abderraouf",new Date()));
        when(post.getId()).thenReturn(null);

        //act
        postService.update(post);
    }

    @Test
    public void update_whenPostIdIsNotNull_thenReturnPost(){
        //arrange
        Post post=spy(new Post("Abderraouf",new Date()));
        Post post1;
        when(post.getId()).thenReturn("222");
        when(postRepository.save((any(Post.class)))).thenReturn(post);

        //act
        post1=postService.update(post);

        //assert
        assertEquals(post,post1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deletePost_whenPostUsernameNotEqualToUsernameGiven_thenThrowIllegalArgumentException(){
        //arrange
        Post post=new Post("Abderraouf",new Date());

        //act
        postService.deletePost(post,"Sami");


    }

    @Test
    public void deletePost_whenPostUsernameIsEqualToUsernameGiven_thenPostIsDeleted(){
        //arrange
        Post post=new Post("Abderraouf",new Date());
        when(postRepository.save((any(Post.class)))).thenReturn(post);
        when(userRepository.getByUsername(anyString())).thenReturn(userModel);
        postService.newPost(post);

        //act
        postService.deletePost(post,"Abderraouf");

        //assert
        assertEquals(0,postRepository.count());

    }

    @Test
    public void getPostById_whenCalled_thenReturnPostWithTheSameId(){
        //arrange
        Post post=spy(new Post("Abderraouf",new Date()));
        Post post1;
        when(post.getId()).thenReturn("222");
        when(postRepository.getPostById(anyString())).thenReturn(post);

        //act
        post1=postService.getPostById("222");

        //assert
        assertEquals(post,post1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void likePost_whenPostLikeIdAlreadyHasGivenUsername_thenThrowIllegalArgumentException(){
        //arrange
        Post post=new Post("Abderraouf", new Date());
        post.addLike(new Like("Abderraouf",new Date()));

        //act
        postService.likePost(post,"Abderraouf");

    }

    @Test
    public void likePost_whenPostLikeIdNotHaveGivenUsername_thenPostIsLiked(){
        //arrange
        Post post=spy(new Post("Abderraouf", new Date()));
        post.addLike(new Like("Abderraouf",new Date()));
        when(post.getId()).thenReturn("222");
        when(postRepository.save((any(Post.class)))).thenReturn(post);

        //act
        postService.likePost(post,"Zaid");

        //assert
        assertEquals(2,post.getLikes().size());


    }

    @Test(expected = IllegalArgumentException.class)
    public void unlikePost_whenPostLikeIdNotHaveGivenUsername_thenThrowIllegalArgumentException(){
        //arrange
        Post post=new Post("Abderraouf", new Date());
        post.addLike(new Like("Abderraouf",new Date()));

        //act
        postService.unlikePost(post,"Zaid");

    }

    @Test
    public void unlikePost_whenPostLikeIdHasGivenUsername_thenPostIsUnliked(){
        //arrange
        Post post=spy(new Post("Abderraouf", new Date()));
        post.addLike(new Like("Abderraouf",new Date()));
        when(post.getId()).thenReturn("222");
        when(postRepository.save((any(Post.class)))).thenReturn(post);

        //act
        postService.unlikePost(post,"Abderraouf");

        //assert
        assertEquals(0,post.getLikes().size());


    }

    @Test
    public void addComment_whenCalled_thenCommentIsPosted(){
        //arrange
        Post post=spy(new Post("Abderraouf", new Date()));
        when(post.getId()).thenReturn("222");
        when(postRepository.save((any(Post.class)))).thenReturn(post);

        //act
        postService.addComment(post,new Comment("Waleed",new Date()));

        //assert
        assertEquals(1,post.getComments().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void removeComment_CommentCreatorNameNotEqualUsername_thenThrowIllegalArgumentException(){
        //arrange
        Post post= spy(new Post("Abderraouf", new Date()));
        Comment comment= new Comment("Waleed",new Date());
        comment.setCommentId("111");

        //act
        postService.removeComment(post,"111","Samer");

    }

    @Test
    public void removeComment_whenCommentCreatorNameEqualUsername_thenCommentIsDeleted(){
        //arrange
        Post post= spy(new Post("Abderraouf", new Date()));
        Comment comment= new Comment("Waleed",new Date());
        comment.setCommentId("111");
        when(post.getId()).thenReturn("222");
        when(postRepository.save((any(Post.class)))).thenReturn(post);
        postService.addComment(post,comment);

        //act
        postService.removeComment(post,"111","Waleed");

        //assert
        assertEquals(0,post.getComments().size());
    }

    @Test
    public void getTrending_whenCalled_thenReturnTop10MostLikedPosts(){ //todo main function not working
        //arrange
        List <Post> list=getPosts();
        List <Post> testedList=new ArrayList<>();
        when((postRepository.findAll())).thenReturn(list);

        //act
        testedList=postService.getTrending();
        list.removeAll(testedList);

        //assert
        assertEquals(list.get(0).getCreatorUsername(),getPosts().get(10).getCreatorUsername());
    }


}
