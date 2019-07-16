package com.shiju.recipe.controllers;

import com.shiju.recipe.domain.Difficulty;
import com.shiju.recipe.dto.IngredientDto;
import com.shiju.recipe.dto.NotesDto;
import com.shiju.recipe.dto.RecipeDto;
import com.shiju.recipe.dto.UnitOfMeasureDto;
import com.shiju.recipe.services.RecipeService;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(IngredientsController.class)
public class IngredientsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void getById() throws Exception {
        RecipeDto recipeDto = buildRecipeDto();
        Mockito.when(recipeService.findCommandById(1L)).thenReturn(Optional.of(recipeDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/recipe/ingredients/list"));
    }

    @Test
    public void getByIdWhenNoMatchFound() throws Exception {
        Mockito.when(recipeService.findCommandById(1L)).thenReturn(Optional.empty());
        expectedException.expect(Matchers.any(Exception.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients"));
        Mockito.verify(recipeService, Mockito.times(1)).findCommandById(1L);
    }

    @Test
    public void getIngredient() throws Exception {
        RecipeDto recipeDto = buildRecipeDto();
        recipeDto.getIngredients().forEach(ingredientDto -> ingredientDto.setRecipeId(recipeDto.getId()));

        Mockito.when(recipeService.findCommandById(1L)).thenReturn(Optional.of(recipeDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredients/1/show"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("ingredient", Matchers.any(IngredientDto.class)))
                .andExpect(MockMvcResultMatchers.view().name("/recipe/ingredient/show"));
    }


    private RecipeDto buildRecipeDto() {
        NotesDto notesDto = NotesDto.builder().id(1L).recipeNotes("recipeNotes").build();
        UnitOfMeasureDto unitOfMeasureDto = UnitOfMeasureDto.builder().id(1L).description("Spoon").build();
        IngredientDto ingredientDto = IngredientDto.builder().
                amount(BigDecimal.ONE).description("ChilliPowder").
                unitOfMeasure(unitOfMeasureDto).id(1L).build();

        return RecipeDto.builder().id(1L).source("Grandmas's Recipe").
                servings(4).prepTime(20).notes(notesDto).direction("serve hot").
                difficulty(Difficulty.MODERATE).description("spicy dish").cookTime(30).
                url("http://shijuppaul.co.uk").ingredients(Collections.singleton(ingredientDto)).build();
    }

}