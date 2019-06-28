package com.shiju.recipe.controllers;

import com.shiju.recipe.domain.Recipe;
import com.shiju.recipe.services.RecipeService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Set;

@RunWith(MockitoJUnitRunner.class)
public class IndexControllerMockMvcStandaloneTest {

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private IndexController indexController;

    private MockMvc mockMvc;

    private Set<Recipe> recipes;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();

        Recipe recipe = new Recipe();
        recipe.setSource("American Mexican");
        recipes = Collections.singleton(recipe);

        Mockito.when(recipeService.getRecipies()).thenReturn(recipes);
    }

    @After
    public void tearDown() throws Exception {
        Mockito.verify(recipeService, Mockito.times(1)).getRecipies();
    }

    @Test
    public void getIndexPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }
}
