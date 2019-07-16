package com.shiju.recipe.services;

import com.shiju.recipe.dto.IngredientDto;

public interface IngredientService {

    IngredientDto save(IngredientDto ingredientDto);

    void delete(IngredientDto ingredientDto);

    void deleteById(Long id);
}
