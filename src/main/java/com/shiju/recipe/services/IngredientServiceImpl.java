package com.shiju.recipe.services;

import com.shiju.recipe.domain.Ingredient;
import com.shiju.recipe.domain.Recipe;
import com.shiju.recipe.domain.UnitOfMeasure;
import com.shiju.recipe.dto.IngredientDto;
import com.shiju.recipe.repositories.IngredientRepository;
import com.shiju.recipe.repositories.RecipeRepository;
import com.shiju.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Component
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;

    private final IngredientRepository ingredientRepository;

    private final UnitOfMeasureRepository unitOfMeasureRepository;

    private final Converter<IngredientDto, Ingredient> ingredientDtoIngredientConverter;

    private final Converter<Ingredient, IngredientDto> ingredientIngredientDtoConverter;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientRepository ingredientRepository, UnitOfMeasureRepository unitOfMeasureRepository, Converter<IngredientDto, Ingredient> ingredientDtoIngredientConverter, Converter<Ingredient, IngredientDto> ingredientIngredientDtoConverter) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientDtoIngredientConverter = ingredientDtoIngredientConverter;
        this.ingredientIngredientDtoConverter = ingredientIngredientDtoConverter;
    }

    @Transactional
    @Override
    public IngredientDto save(IngredientDto ingredientDto) {
        Recipe recipe = recipeRepository.findById(ingredientDto.getRecipeId()).get();
        UnitOfMeasure unitOfMeasure = unitOfMeasureRepository.findById(ingredientDto.getUnitOfMeasure().getId()).get();

        Ingredient ingredient = ingredientDtoIngredientConverter.convert(ingredientDto);
        ingredient.setUnitOfMeasure(unitOfMeasure);

        recipe.setIngredients(recipe.getIngredients().stream().filter(ingredient1 -> !ingredient1.getId().equals(ingredient.getId())).collect(Collectors.toSet()));
        ingredient.setRecipe(recipe);
        recipe.getIngredients().add(ingredient);

        recipeRepository.save(recipe);
        return ingredientIngredientDtoConverter.convert(recipe.getIngredients().stream().
                filter(ingredient1 -> ingredient1.getDescription().equals(ingredient.getDescription()) &&
                        ingredient.getUnitOfMeasure().getId().equals(ingredient1.getUnitOfMeasure().getId()))
                .findFirst().get());
    }

    @Override
    public void delete(IngredientDto ingredientDto) {
        ingredientRepository.deleteById(ingredientDto.getId());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        Ingredient ingredient = ingredientRepository.findById(id).get();
        Recipe recipe = recipeRepository.findById(ingredient.getRecipe().getId()).get();
        ingredient.setRecipe(null);
//
//
//        recipe.setIngredients(recipe.getIngredients().stream().filter(ingredient1 -> !ingredient1.getId().equals(ingredient.getId())).collect(Collectors.toSet()));
//        ingredientRepository.deleteById(id);
        recipeRepository.save(recipe);
    }
}
