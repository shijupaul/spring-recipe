package com.shiju.recipe.services;

import com.shiju.recipe.domain.Recipe;
import com.shiju.recipe.repositories.RecipeRepository;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    private Set<Recipe> recipes;

    @Before
    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(recipeRepository);

        Recipe recipe = new Recipe();
        recipe.setSource("street food found somewhere in India......");
        recipes = Collections.singleton(recipe);
    }

    @Test
    public void getRecipies() {
        Mockito.when(recipeRepository.findAll()).thenReturn(recipes);
        Set<Recipe> result =  recipeService.getRecipies();
        Assert.assertThat("No of Recipies don't match", result.size(), Matchers.is(1));
        Assert.assertThat("Recipe Source don't match.", result.iterator().next().getSource(), Matchers.is(recipes.iterator().next().getSource()));
    }

    @After
    public void tearDown() throws Exception {
        Mockito.verify(recipeRepository, Mockito.times(1)).findAll();
    }
}