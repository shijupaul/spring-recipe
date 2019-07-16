package com.shiju.recipe.services;

import com.shiju.recipe.domain.Recipe;
import com.shiju.recipe.dto.RecipeDto;

import java.util.Optional;
import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipies();
    Optional<Recipe> findById(Long id);
    Optional<RecipeDto> findCommandById(Long id);
    RecipeDto save(RecipeDto recipeDto);
    void delete(Long id);
}
