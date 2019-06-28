package com.shiju.recipe.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CategoryTest {

    private Category category;

    @Before
    public void setup() {
        category = new Category();
        category.setId(1L);
        category.setName("American-Cusine");
        Recipe recipe = new Recipe();
        recipe.setSource("American-Burger");
        category.setRecipes(Collections.singleton(recipe));
    }

    @Test
    public void getId() {
        assertThat("Id should match", category.getId(), is(1L));
    }

    @Test
    public void getName() {
        assertThat("Name should match.", category.getName(), is("American-Cusine"));
    }

    @Test
    public void getRecipes() {
        assertThat("Recipe count don't match", category.getRecipes().size(), is(1));
        assertThat("Recipe Source don't match", category.getRecipes().iterator().next().getSource(), is("American-Burger"));
    }
}