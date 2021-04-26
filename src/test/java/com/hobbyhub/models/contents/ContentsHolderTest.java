package com.hobbyhub.models.contents;

import com.hobbyhub.models.categories.CategoriesHolder;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;


public class ContentsHolderTest {


    @Test
    public void addContentList_whenContentPairIsNull_thenReturnFalse(){
        //arrange
        ContentsHolder contentsHolder=new ContentsHolder();
        ContentPair contentPair=null;
        boolean isAdded;

        //act
        isAdded=contentsHolder.addContent(contentPair);

        //assert
        assertFalse(isAdded);

    }

    @Test
    public void addContentList_whenContentPairIsNotNull_thenReturnTrue(){
        //arrange
        ContentsHolder contentsHolder=new ContentsHolder();
        ContentPair contentPair=new ContentPair();
        boolean isAdded;

        //act
        isAdded=contentsHolder.addContent(contentPair);

        //assert
        assertTrue(isAdded);
    }
    @Test
    public void removeContent_whenContentIsInList_thenReturnTrue(){
        //arrange
        ContentsHolder contentsHolder=new ContentsHolder();
        ContentPair contentPair= new ContentPair();
        contentsHolder.addContent(contentPair);
        boolean isRemoved;

        //act
        isRemoved=contentsHolder.removeContent(contentPair);

        //assert
        assertTrue(isRemoved);
    }
    @Test
    public void removeContent_whenContentIsNotInList_thenReturnFalse(){
        //arrange
        ContentsHolder contentsHolder=new ContentsHolder();
        ContentPair contentPair= new ContentPair();
        boolean isRemoved;

        //act
        isRemoved=contentsHolder.removeContent(contentPair);

        //assert
        assertFalse(isRemoved);
    }


    @Test(expected = IndexOutOfBoundsException.class)
    public void removeContent_whenIndexIsLessThan0_thenThrowIndexOutOfBoundsException(){
        //arrange
        ContentsHolder contentsHolder=new ContentsHolder();
        ContentPair contentPair= new ContentPair();
        contentsHolder.addContent(contentPair);
        boolean isAdded;

        //act
        contentsHolder.removeContent(-1);

    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void removeContent_whenIndexIsBiggerThanListSize_thenThrowIndexOutOfBoundsException(){
        //arrange
        ContentsHolder contentsHolder=new ContentsHolder();
        ContentPair contentPair= new ContentPair();
        contentsHolder.addContent(contentPair);
        boolean isAdded;

        //act
        contentsHolder.removeContent(contentsHolder.getNumberOfContent()+1);
    }
}
