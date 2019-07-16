package com.shiju.recipe.services;

import com.shiju.recipe.domain.Recipe;
import com.shiju.recipe.dto.RecipeDto;
import com.shiju.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl  implements RecipeService {

    private final RecipeRepository recipeRepository;

    private final Converter<RecipeDto, Recipe> recipeDtoRecipeConverter;

    private final Converter<Recipe, RecipeDto> recipeRecipeDtoConverter;

    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             Converter<RecipeDto, Recipe> recipeDtoRecipeConverter,
                             Converter<Recipe, RecipeDto> recipeRecipeDtoConverter
    ) {
        System.out.println("**************");
        this.recipeRepository = recipeRepository;
        this.recipeDtoRecipeConverter = recipeDtoRecipeConverter;
        this.recipeRecipeDtoConverter = recipeRecipeDtoConverter;
    }

    @Override
    public Set<Recipe> getRecipies() {
        log.debug("Hello it's me.");
        Set<Recipe> recipies = new HashSet();
        recipeRepository.findAll().forEach(recipies::add);
        return recipies;
    }

    @Override
    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }

    @Nullable
    @Transactional
    @Override
    public RecipeDto save(RecipeDto recipeDto) {
        Recipe recipe = recipeDtoRecipeConverter.convert(recipeDto);
        if (recipe == null) return null;
        Recipe savedRecipe = recipeRepository.save(recipe);
        return recipeRecipeDtoConverter.convert(savedRecipe);
    }

    @Transactional // to handle lazy loaded properties
    @Override
    public Optional<RecipeDto> findCommandById(Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);
        if (recipeOptional.isPresent()) {
            return Optional.of(recipeRecipeDtoConverter.convert(recipeOptional.get()));
        }
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        recipeRepository.deleteById(id);
    }
}
