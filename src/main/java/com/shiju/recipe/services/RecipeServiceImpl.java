package com.shiju.recipe.services;

import com.shiju.recipe.domain.Recipe;
import com.shiju.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl  implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipies() {
        log.debug("Hello it's me.");
        Set<Recipe> recipies = new HashSet();
        recipeRepository.findAll().forEach(recipies::add);
        return recipies;
    }
}
