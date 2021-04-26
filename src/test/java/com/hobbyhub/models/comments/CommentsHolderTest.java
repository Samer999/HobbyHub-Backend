package com.hobbyhub.models.comments;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.time.Instant;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class CommentsHolderTest {



    @Test
    public void removeComment_whenCalled_thenCommentIsRemovedFromListOfComments(){
        //arrange
        CommentsHolder commentsHolder=new CommentsHolder();
        commentsHolder.addComment(new Comment());
        commentsHolder.addComment(new Comment());
        commentsHolder.getComments().get(1).setCommentId("2");

        //act
        commentsHolder.removeComment("2");

        //assert
        assertEquals(1,commentsHolder.getNumberOfComments());


    }

    @Test(expected = NullPointerException.class)
    public void addComment_whenCommentIsnull_thenThrowNullPointerException(){
        //arrange
        CommentsHolder commentsHolder=new CommentsHolder();
        Comment comment=null;

        //act
        commentsHolder.addComment(comment);



    }
    @Test(expected = IllegalArgumentException.class)
    public void addComment_whenCommentExistInList_thenThrowIllegalArgumentException(){
        //arrange
        CommentsHolder commentsHolder=new CommentsHolder();
        Comment comment=new Comment();

        //act
        commentsHolder.addComment(comment);
        commentsHolder.addComment(comment);



    }

    @Test
    public void addComment_whenCommentNotExistInList_thenAddCommentToList(){
        //arrange
        CommentsHolder commentsHolder=new CommentsHolder();
        Comment comment=new Comment();

        //act
        commentsHolder.addComment(comment);

        //assert
        assertEquals(1,commentsHolder.getNumberOfComments());


    }

    @Test
    public void setComments_whenCalled_thenReturnTheListOfCommentsInHolder(){
        //arrange
        CommentsHolder commentsHolder=new CommentsHolder();
        List<Comment> list= new ArrayList<>();

        //act
        commentsHolder.setComments(list);

        //assert
        assertEquals(list,commentsHolder.getComments());


    }

    @Test(expected = NullPointerException.class)
    public void commentIdExists_whenCommentIdIsNull_thenThrowNullPointerException(){
        //arrange
        CommentsHolder commentsHolder=new CommentsHolder();
        Comment comment=new Comment();
        comment.setCommentId(null);

        //act
        commentsHolder.commentIdExists(null);

    }

    @Test
    public void commentIdExists_whenCommentIdExistsInList_thenReturnTrue(){
        //arrange
        CommentsHolder commentsHolder=new CommentsHolder();
        Comment comment=new Comment();
        comment.setCommentId("2");
        commentsHolder.addComment(comment);
        boolean isExists;

        //act
        isExists=commentsHolder.commentIdExists("2");

        //assert
        assertTrue(isExists);

    }

    @Test
    public void commentIdExists_whenCommentIdNotExistInList_thenReturnFalse(){
        //arrange
        CommentsHolder commentsHolder=new CommentsHolder();
        Comment comment=new Comment();
        comment.setCommentId("2");
        boolean isExists;
        //act
        isExists=commentsHolder.commentIdExists("1");

        //assert
        assertFalse(isExists);
    }

    @Test
    public void getCommentById_whenCommentExistInList_thenReturnComment(){
        //arrange
        CommentsHolder commentsHolder=new CommentsHolder();
        Comment comment=new Comment();
        comment.setCommentId("2");
        commentsHolder.addComment(comment);

        //act
        commentsHolder.getCommentById("2");

        //assert
        assertEquals(comment,commentsHolder.getCommentById("2"));

    }

    @Test(expected = IllegalArgumentException.class)
    public void getCommentById_whenCommentNotExistInList_thenThrowIllegalArgumentException(){
        //arrange
        CommentsHolder commentsHolder=new CommentsHolder();
        Comment comment=new Comment();
        comment.setCommentId("2");

        //act
        commentsHolder.getCommentById("1");

    }

    @Test
    public void getNumberOfComments_whenCalled_thenReturnSizeOfListOfComments(){
        //arrange
        CommentsHolder commentsHolder=new CommentsHolder();
        Comment comment=new Comment();

        //act
        commentsHolder.addComment(comment);

        //assert
        assertEquals(1,commentsHolder.getNumberOfComments());

    }



}
