package com.shiju.recipe.controllers;

import com.shiju.recipe.domain.Recipe;
import com.shiju.recipe.services.RecipeService;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class IndexControllerTest {

    @Mock
    private RecipeService recipeService;

    @Mock
    private Model model;

    @InjectMocks
    private IndexController indexController;

    private Set<Recipe> recipes;

    private ArgumentCaptor<Set> argumentCaptor;

    @Before
    public void setUp() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setSource("Indo American");
        recipes = Collections.singleton(recipe);

        argumentCaptor = ArgumentCaptor.forClass(Set.class);

        Mockito.when(recipeService.getRecipies()).thenReturn(recipes);
//        Mockito.when(model.addAttribute("recipies", recipes)).thenReturn(model);
    }

    @After
    public void tearDown() throws Exception {
        Mockito.verify(recipeService, Mockito.times(1)).getRecipies();
        Mockito.verify(model, Mockito.times(1)).addAttribute(Mockito.eq("recipies"), argumentCaptor.capture());
        Assert.assertThat("recipies don't match", recipes, Matchers.is(argumentCaptor.getValue()));
    }

    @Test
    public void getIndexPage() {
        Assert.assertThat("Returned path don't match", indexController.getIndexPage(model), Matchers.is("index"));
    }

}