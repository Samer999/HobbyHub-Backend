package com.hobbyhub.models.categories;

import com.hobbyhub.models.comments.CommentsHolder;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

public class CategoriesHolderTest {




    @Test
    public void addCategories_whenCategoryIsAdded_thenReturnTrue(){
        //arrange
        CategoriesHolder categoriesHolder=new CategoriesHolder();
        boolean isAdded;

        //act
        isAdded=categoriesHolder.addCategoryTag("Tennis");

        //assert
       assertTrue(isAdded);
    }
   @Test
    public void removeCategories_whenCategoryExistsInSet_thenReturnTrue(){
        //arrange
        CategoriesHolder categoriesHolder=new CategoriesHolder();
        categoriesHolder.addCategoryTag("Tennis");
        categoriesHolder.addCategoryTag("Baseball");
        boolean isRemoved;

        //act
       isRemoved=categoriesHolder.removeCategoryTag("Tennis");

        //assert
        assertTrue(isRemoved);
    }
    @Test
    public void removeCategories_whenCategoryDoesNotExistsInSet_thenReturnFalse(){
        //arrange
        CategoriesHolder categoriesHolder=new CategoriesHolder();
        boolean isRemoved;

        //act
        isRemoved=categoriesHolder.removeCategoryTag("Tennis");

        //assert
        assertFalse(isRemoved);

    }
}
