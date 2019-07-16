package com.shiju.recipe.converter;

import com.shiju.recipe.domain.Category;
import com.shiju.recipe.domain.Ingredient;
import com.shiju.recipe.domain.Notes;
import com.shiju.recipe.domain.Recipe;
import com.shiju.recipe.dto.CategoryDto;
import com.shiju.recipe.dto.IngredientDto;
import com.shiju.recipe.dto.NotesDto;
import com.shiju.recipe.dto.RecipeDto;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class RecipeDtoToRecipeConverter implements Converter<RecipeDto, Recipe> {

    private final Converter<NotesDto, Notes> notesDtoToNotesConverter;

    private final Converter<IngredientDto, Ingredient> ingredientDtoIngredientConverter;

    private final Converter<CategoryDto, Category> categoryDtoCategoryConverter;

    @Nullable
    @Synchronized
    @Override
    public Recipe convert(RecipeDto recipeDto) {
        if (recipeDto == null) return null;

        Notes notes = null;
        if (recipeDto.getNotes() != null) {
            notes = notesDtoToNotesConverter.convert(recipeDto.getNotes());
        }

        Set<Ingredient> ingredients = new HashSet<>();
        if (!CollectionUtils.isEmpty(recipeDto.getIngredients())) {
            ingredients = recipeDto.getIngredients().stream().
                    map(ingredientDtoIngredientConverter::convert).
                    collect(Collectors.toSet());
        }

        Set<Category> categories = new HashSet<>();
        if (!CollectionUtils.isEmpty(recipeDto.getCategories())) {
            categories = recipeDto.getCategories().stream().
                    map(categoryDtoCategoryConverter::convert).
                    collect(Collectors.toSet());
        }

        Recipe recipe = Recipe.builder().categories(categories).cookTime(recipeDto.getCookTime()).
                description(recipeDto.getDescription()).difficulty(recipeDto.getDifficulty()).
                direction(recipeDto.getDirection()).id(recipeDto.getId()).
                ingredients(ingredients).notes(notes).prepTime(recipeDto.getPrepTime()).
                servings(recipeDto.getServings()).source(recipeDto.getSource()).
                url(recipeDto.getUrl()).build();
        notes.setRecipe(recipe);
        ingredients.forEach(ingredient -> ingredient.setRecipe(recipe));
        categories.forEach(category -> category.getRecipes().add(recipe));

        return recipe;
    }
}
