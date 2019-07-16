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
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Base64;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class RecipeToRecipeDtoConverter implements Converter<Recipe, RecipeDto> {

    private final Converter<Category, CategoryDto> categoryCategoryDtoConverter;

    private final Converter<Ingredient, IngredientDto> ingredientIngredientDtoConverter;

    private final Converter<Notes, NotesDto> notesNotesDtoConverter;

    @Nullable
    @Synchronized
    @Override
    public RecipeDto convert(Recipe recipe) {
        if (recipe == null) return null;

        Set<IngredientDto> ingredients = new HashSet<>();
        if (!CollectionUtils.isEmpty(recipe.getIngredients())) {
            ingredients = recipe.getIngredients().stream().
                    map(ingredientIngredientDtoConverter::convert).collect(Collectors.toSet());
        }
        Set<CategoryDto> categories = new HashSet<>();
        if (!CollectionUtils.isEmpty(recipe.getIngredients())) {
            categories = recipe.getCategories().stream().
                    map(category -> (CategoryDto) categoryCategoryDtoConverter.convert(category)).collect(Collectors.toSet());
        }
        NotesDto notes = null;
        if (recipe.getNotes() != null) {
            notes = notesNotesDtoConverter.convert(recipe.getNotes());
        }

        String base64EncodedImageString = null;
        if (recipe.getImage() != null && recipe.getImage().length > 0) {
            base64EncodedImageString = Base64.getEncoder().encodeToString(ArrayUtils.toPrimitive(recipe.getImage()));
        }
        return RecipeDto.builder().categories(categories).cookTime(recipe.getCookTime()).
                description(recipe.getDescription()).difficulty(recipe.getDifficulty()).direction(recipe.getDirection()).
                id(recipe.getId()).ingredients(ingredients).notes(notes).prepTime(recipe.getPrepTime()).
                servings(recipe.getServings()).source(recipe.getSource()).image(base64EncodedImageString).build();
    }
}
