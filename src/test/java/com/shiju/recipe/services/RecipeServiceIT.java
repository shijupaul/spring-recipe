package com.shiju.recipe.services;

import com.shiju.recipe.domain.Category;
import com.shiju.recipe.domain.Difficulty;
import com.shiju.recipe.domain.UnitOfMeasure;
import com.shiju.recipe.dto.*;
import com.shiju.recipe.repositories.CategoryRepository;
import com.shiju.recipe.repositories.UnitOfMeasureRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    private Converter<Category, CategoryDto> categoryCategoryDtoConverter;

    @Autowired
    private Converter<UnitOfMeasure, UnitOfMeasureDto> unitOfMeasureUnitOfMeasureDtoConverter;

    @Transactional // Will rollback transaction at the end of test -- remove db entry created
    @Test
    public void testSave() {

        CategoryDto categoryDtoAmerican = getCategoryDto("American");
        CategoryDto categoryDtoMexican = getCategoryDto("Mexican");
        UnitOfMeasureDto unitOfMeasureDtoTeaspoon = getUnitOfMeasureDto("Teaspoon");
        UnitOfMeasureDto unitOfMeasureDtoPinch = getUnitOfMeasureDto("Pinch");

        NotesDto notesDto = NotesDto.builder().recipeNotes("Special Recipe from Shiju Paul").build();
        IngredientDto ingredientDtoSalt = IngredientDto.builder().amount(BigDecimal.valueOf(2)).description("Salt").unitOfMeasure(unitOfMeasureDtoPinch).build();
        IngredientDto ingredientDtoSpices = IngredientDto.builder().amount(BigDecimal.valueOf(1.5)).description("Spices").unitOfMeasure(unitOfMeasureDtoTeaspoon).build();

        Set<IngredientDto> ingredients = new HashSet<>();
        ingredients.add(ingredientDtoSalt);
        ingredients.add(ingredientDtoSpices);

        Set<CategoryDto> categories = new HashSet<>();
        categories.add(categoryDtoAmerican);
        categories.add(categoryDtoMexican);

        RecipeDto recipeDto = RecipeDto.builder().source("From my Grandmas").
                servings(4).prepTime(20).notes(notesDto).ingredients(ingredients).
                direction("South indian dish everyone likes").difficulty(Difficulty.MODERATE).
                description("It's going to be a mouth watering dish").cookTime(30).
                categories(categories).url("http://shijuppaul.co.uk").build();

        RecipeDto recipeDtoSaved = recipeService.save(recipeDto);
        verifyRecipeDto(recipeDtoSaved);
    }


    private CategoryDto getCategoryDto(String name) {
        return categoryCategoryDtoConverter.convert(categoryRepository.findOneByName(name).get());
    }

    private UnitOfMeasureDto getUnitOfMeasureDto(String description) {
        return unitOfMeasureUnitOfMeasureDtoConverter.convert(unitOfMeasureRepository.findByDescription(description).get());
    }

    private void verifyRecipeDto(RecipeDto recipeDto) {
        Assert.assertNotNull("Recipe Id Should not be Null", recipeDto.getId());
        Assert.assertNotNull("Note should not be null", recipeDto.getNotes());
        Assert.assertNotNull("Note Id should not be null", recipeDto.getNotes().getId());
        for (IngredientDto ingredientDto: recipeDto.getIngredients()) {
            Assert.assertNotNull("Ingredient Id should not be null", ingredientDto.getId());
            Assert.assertNotNull("Unit of Measurement not null", ingredientDto.getUnitOfMeasure());
            Assert.assertNotNull("Unit of Measurement Id not null", ingredientDto.getUnitOfMeasure().getId());
        }
        for (CategoryDto categoryDto: recipeDto.getCategories()) {
            Assert.assertNotNull("Category Id is not null", categoryDto.getId());
        }

    }
}
