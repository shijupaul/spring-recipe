package com.shiju.recipe.services;

import com.shiju.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipies();
}
