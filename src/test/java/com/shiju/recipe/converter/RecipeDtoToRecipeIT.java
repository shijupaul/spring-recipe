package com.shiju.recipe.converter;

import com.shiju.recipe.domain.Difficulty;
import com.shiju.recipe.domain.Recipe;
import com.shiju.recipe.dto.*;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeDtoToRecipeIT {

    @Autowired
    private Converter<RecipeDto, Recipe> recipeDtoRecipeConverter;

    @Test
    public void testConvert() {
        Recipe recipe = recipeDtoRecipeConverter.convert(buildRecipeDto());
        verify(recipe);
    }

    private RecipeDto buildRecipeDto() {
        NotesDto notesDto = NotesDto.builder().recipeNotes("Recipe Note....").id(1L).build();

        CategoryDto categoryDtoAmerican = CategoryDto.builder().name("American").id(1L).build();
        CategoryDto categoryDtoItalian = CategoryDto.builder().name("Italian").id(2L).build();
        Set<CategoryDto> categories = new HashSet<>();
        categories.add(categoryDtoAmerican);
        categories.add(categoryDtoItalian);

        UnitOfMeasureDto unitOfMeasureDtoSpoon = UnitOfMeasureDto.builder().id(1L).description("Spoon").build();
        UnitOfMeasureDto unitOfMeasureDtoPinch = UnitOfMeasureDto.builder().id(1L).description("Pinch").build();

        IngredientDto ingredientDtoChilli = IngredientDto.builder().id(1L).amount(BigDecimal.valueOf(2)).
                description("Chilli Powder").unitOfMeasure(unitOfMeasureDtoSpoon).build();
        IngredientDto ingredientDtoSalt = IngredientDto.builder().id(2L).amount(BigDecimal.valueOf(4)).
                description("Salt").unitOfMeasure(unitOfMeasureDtoPinch).build();

        Set<IngredientDto> ingredients = new HashSet<>();
        ingredients.add(ingredientDtoChilli);
        ingredients.add(ingredientDtoSalt);

        return RecipeDto.builder().url("http://shijuppaul.co.uk").categories(categories).
                cookTime(30).description("Recipe from my Grand mother").difficulty(Difficulty.MODERATE).
                direction("handle with care").ingredients(ingredients).notes(notesDto).prepTime(20).
                servings(4).source("awesome").id(1L).build();
    }

    private void verify(Recipe recipe) {
        Assert.assertNotNull("Recipe is not null", recipe);
        Assert.assertThat("Recipe Id don't match", recipe.getId(), Matchers.is(1L));

        System.out.println("Matching Note.");
        Assert.assertNotNull("Note should not be null", recipe.getNotes());
        Assert.assertThat("Note Id don't match", recipe.getNotes().getId(), Matchers.is(1L));
        Assert.assertNotNull("Should have recipe object", recipe.getNotes().getRecipe());

        System.out.println("Recipe Category link");
        Assert.assertThat("Two categories should be there", recipe.getCategories().size(), Matchers.is(2));

        System.out.println("Recipe Ingredients Link");
        Assert.assertThat("Should be two ingredients", recipe.getIngredients().size(), Matchers.is(2));
        recipe.getIngredients().forEach(ingredient -> {
            Assert.assertNotNull("Recipe mapping should be there", ingredient.getRecipe());
        });
    }
}
