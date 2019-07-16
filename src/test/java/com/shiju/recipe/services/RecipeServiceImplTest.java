package com.shiju.recipe.services;

import com.shiju.recipe.domain.Recipe;
import com.shiju.recipe.repositories.RecipeRepository;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;
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
        recipe.setId(1L);
        recipes = Collections.singleton(recipe);
    }

    @Test
    public void getRecipies() {
        Mockito.when(recipeRepository.findAll()).thenReturn(recipes);
        Set<Recipe> result =  recipeService.getRecipies();
        Assert.assertThat("No of Recipies don't match", result.size(), Matchers.is(1));
        Assert.assertThat("Recipe Source don't match.", result.iterator().next().getSource(), Matchers.is(recipes.iterator().next().getSource()));

        Mockito.verify(recipeRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void getRecipeById() {
        Mockito.when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipes.iterator().next()));

        Optional<Recipe> recipeOptional = recipeService.findById(1L);
        Assert.assertNotNull("Expected recipe", recipeOptional.get());

        Mockito.verify(recipeRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    public void deleteRecipe() {
        recipeService.delete(1L);
        Mockito.verify(recipeRepository, Mockito.times(1)).deleteById(1L);
    }

}