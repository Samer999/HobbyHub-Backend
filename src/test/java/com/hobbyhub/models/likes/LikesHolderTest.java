package com.hobbyhub.models.likes;

import com.hobbyhub.models.categories.CategoriesHolder;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class LikesHolderTest {


    @Test(expected = NullPointerException.class)
    public void addLike_whenLikeIsNull_thenThrowNullPointerException(){
        //arrange
        LikesHolder likesHolder=new LikesHolder();
        Like like=null;

        //act
        likesHolder.addLike(like);

    }

    @Test(expected = IllegalArgumentException.class)
    public void addLike_whenLikeIdAlreadyExists_thenThrowIllegalArgumentException(){
        //arrange
        LikesHolder likesHolder=new LikesHolder();
        Like like= new Like();
        like.setLikeId("1");
        likesHolder.addLike(like);

        //act
        likesHolder.addLike(like);

    }

    @Test
    public void addLike_whenLikeIsNotInList_thenAddToList(){
        //arrange
        LikesHolder likesHolder=new LikesHolder();
        Like like= new Like();
        like.setLikeId("1");

        //act
        likesHolder.addLike(like);

        //assert
        assertEquals(1,likesHolder.getNumberOfLikes());
    }

    @Test
    public void removeLike_whenLikeIdExistInList_thenRemoveFromList(){
        //arrange
        LikesHolder likesHolder=new LikesHolder();
        Like like= new Like();
        like.setLikeId("1");
        likesHolder.addLike(like);

        //act
        likesHolder.removeLike("1");

        //assert
        assertEquals(0,likesHolder.getNumberOfLikes());
    }

    @Test(expected = NullPointerException.class)
    public void likeIdExists_whenIdIsNull_thenThrowNullPointerException(){
        //arrange
        LikesHolder likesHolder=new LikesHolder();
        Like like= new Like();
        like.setLikeId(null);

        //act
        likesHolder.likeIdExists(null);

    }

    @Test
    public void likeIdExists_whenIdExists_thenReturnTrue(){
        //arrange
        LikesHolder likesHolder=new LikesHolder();
        Like like= new Like();
        like.setLikeId("1");
        likesHolder.addLike(like);
        boolean isExist;

        //act
        isExist=likesHolder.likeIdExists("1");

        //assert
        assertTrue(isExist);
    }

    @Test
    public void likeIdExists_whenIdNotExists_thenReturnFalse(){
        //arrange
        LikesHolder likesHolder=new LikesHolder();
        Like like= new Like();
        like.setLikeId("1");
        likesHolder.addLike(like);
        boolean isExist;

        //act
        isExist=likesHolder.likeIdExists("2");

        //assert
        assertFalse(isExist);
    }

    @Test
    public void getLikeById_whenIdExist_thenReturnLike(){
        //arrange
        LikesHolder likesHolder=new LikesHolder();
        Like like= new Like();
        like.setLikeId("1");
        likesHolder.addLike(like);

        //act
        Like like1=likesHolder.getLikeById("1");

        //assert
        assertEquals(like1,likesHolder.getLikes().get(0));

    }

    @Test(expected = IllegalArgumentException.class)
    public void getLikeById_whenIdNotExist_thenThrowIllegalArgumentException(){
        //arrange
        LikesHolder likesHolder=new LikesHolder();
        Like like= new Like();
        like.setLikeId("1");
        likesHolder.addLike(like);
        boolean isExist;

        //act
        Like like1=likesHolder.getLikeById("2");

    }


}
