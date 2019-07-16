package com.shiju.recipe.converter;

import com.shiju.recipe.domain.*;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeToRecipeDtoIT {

    @Autowired
    private Converter<Recipe, RecipeDto> recipeRecipeDtoConverter;

    @Test
    public void convert() {
        RecipeDto recipeDto = recipeRecipeDtoConverter.convert(buildRecipe());
        verify(recipeDto);
    }

    private Recipe buildRecipe() {
        Notes notes = Notes.builder().recipeNotes("Recipe Note....").id(1L).build();

        Category categoryAmerican = Category.builder().name("American").id(1L).build();
        Category categoryItalian = Category.builder().name("Italian").id(2L).build();
        Set<Category> categories = new HashSet<>();
        categories.add(categoryAmerican);
        categories.add(categoryItalian);

        UnitOfMeasure unitOfMeasureSpoon = UnitOfMeasure.builder().id(1L).description("Spoon").build();
        UnitOfMeasure unitOfMeasurePinch = UnitOfMeasure.builder().id(1L).description("Pinch").build();

        Ingredient ingredientChilli = Ingredient.builder().id(1L).amount(BigDecimal.valueOf(2)).
                description("Chilli Powder").unitOfMeasure(unitOfMeasureSpoon).build();
        Ingredient ingredientSalt = Ingredient.builder().id(2L).amount(BigDecimal.valueOf(4)).
                description("Salt").unitOfMeasure(unitOfMeasurePinch).build();

        Set<Ingredient> ingredients = new HashSet<>();
        ingredients.add(ingredientChilli);
        ingredients.add(ingredientSalt);

        Recipe recipe = Recipe.builder().url("http://shijuppaul.co.uk").categories(categories).
                cookTime(30).description("Recipe from my Grand mother").difficulty(Difficulty.MODERATE).
                direction("handle with care").ingredients(ingredients).notes(notes).prepTime(20).
                servings(4).source("awesome").id(1L).build();

        notes.setRecipe(recipe);
        categories.forEach(category -> category.setRecipes(Collections.singleton(recipe)));
        ingredients.forEach(ingredient -> ingredient.setRecipe(recipe));

        return recipe;
    }

    private void verify(RecipeDto recipeDto) {
        Assert.assertNotNull("Recipe Dto should not be null", recipeDto);
        Assert.assertNotNull("Id should be set", recipeDto.getId());
        Assert.assertNotNull("Notes should not be null", recipeDto.getNotes());
        Assert.assertThat("Should be two ingredients", recipeDto.getIngredients().size(), Matchers.is(2));
        Assert.assertThat("Should be two categories", recipeDto.getCategories().size(), Matchers.is(2));
    }
}
