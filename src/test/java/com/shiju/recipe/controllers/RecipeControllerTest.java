package com.shiju.recipe.controllers;

import com.shiju.recipe.domain.Category;
import com.shiju.recipe.domain.Difficulty;
import com.shiju.recipe.domain.Notes;
import com.shiju.recipe.domain.Recipe;
import com.shiju.recipe.dto.NotesDto;
import com.shiju.recipe.dto.RecipeDto;
import com.shiju.recipe.services.ImageService;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.Optional;


@RunWith(SpringRunner.class)
@WebMvcTest(RecipeController.class)
public class RecipeControllerTest {

    @MockBean
    private RecipeService recipeService;

    @MockBean
    private ImageService imageService;

    @Autowired
    private MockMvc mockMvc;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void getById() throws Exception {
        Recipe recipe = buildRecipe();
        Mockito.when(recipeService.findById(1L)).thenReturn(Optional.of(recipe));

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/show"))
                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.model().attribute("recipe", Matchers.is(recipe)))
                .andExpect(MockMvcResultMatchers.view().name("recipe/show"));
    }

    @Test
    public void getByIdWhenNoMatchFound() throws Exception {
        Mockito.when(recipeService.findById(1L)).thenReturn(Optional.empty());
        expectedException.expect(Matchers.any(Exception.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/show"));
    }

    @Test
    public void createNewRecipe() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/new"))
                .andExpect(MockMvcResultMatchers.view().name(Matchers.is("recipe/recipeform")))
                .andExpect(MockMvcResultMatchers.model().attribute("recipe", Matchers.any(RecipeDto.class)));
    }

    @Test
    public void retrieveRecipeDtoForUpdate() throws Exception {
        Mockito.when(recipeService.findCommandById(1L)).thenReturn(Optional.of(buildRecipeDto()));

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/update"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/recipeform"))
                .andExpect(MockMvcResultMatchers.model().attribute("recipe", Matchers.any(RecipeDto.class)));
    }

    @Test
    public void retrieveRecipeDtoForUpdateWhenNoMatch() throws Exception {
        Mockito.when(recipeService.findCommandById(1L)).thenReturn(Optional.empty());

        expectedException.expect(Matchers.any(Exception.class));

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/update"));
    }

    @Test
    public void saveOrUpdate() throws Exception {
        RecipeDto recipeDto = buildRecipeDto();

        Mockito.when(recipeService.save(Mockito.any(RecipeDto.class))).thenReturn(recipeDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/recipe").
                contentType(MediaType.APPLICATION_FORM_URLENCODED).
                param("id", "1").
                param("description", "my first recipe")
        )
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/1/show/"));
    }

    @Test
    public void testDeleteRecipe() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/delete")).
                andExpect(MockMvcResultMatchers.status().is3xxRedirection()).
                andExpect(MockMvcResultMatchers.view().name("redirect:/"));

        Mockito.verify(recipeService, Mockito.times(1)).delete(1L);
    }

    @Test
    public void testUploadImage() throws Exception {
        RecipeDto recipeDto = buildRecipeDto();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("fileToUpload", "testing.txt", "text/plain", "this is my image".getBytes());

        Mockito.doNothing().when(imageService).storeImage(1L, mockMultipartFile);
        Mockito.when(recipeService.findCommandById(1L)).thenReturn(Optional.of(recipeDto));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/recipe/1/image").file(mockMultipartFile)).
                andExpect(MockMvcResultMatchers.status().is2xxSuccessful()).
                andExpect(MockMvcResultMatchers.model().attribute("recipe", recipeDto)).
                andExpect(MockMvcResultMatchers.view().name("recipe/imageuploadform"));

    }

    private Recipe buildRecipe() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setPrepTime(10);
        recipe.setCookTime(5);
        recipe.setServings(4);
        recipe.setSource("Test Source");
        recipe.setUrl("test url");
        recipe.setDirection("test direction");

        Notes notes = new Notes();
        notes.setRecipeNotes("test notes");
        recipe.setNotes(notes);

        recipe.setDifficulty(Difficulty.MODERATE);

        Category category = new Category();
        category.setName("American");
        recipe.setCategories(Collections.singleton(category));

        return recipe;
    }

    private RecipeDto buildRecipeDto() {
        NotesDto notesDto = NotesDto.builder().id(1L).recipeNotes("recipeNotes").build();
        return RecipeDto.builder().id(1L).source("Grandmas's Recipe").
                servings(4).prepTime(20).notes(notesDto).direction("serve hot").
                difficulty(Difficulty.MODERATE).description("spicy dish").cookTime(30).
                url("http://shijuppaul.co.uk").build();
    }
}