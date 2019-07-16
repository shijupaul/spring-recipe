package com.shiju.recipe.converter;

import com.shiju.recipe.domain.Ingredient;
import com.shiju.recipe.domain.UnitOfMeasure;
import com.shiju.recipe.dto.IngredientDto;
import com.shiju.recipe.dto.UnitOfMeasureDto;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class IngredientDtoToIngredientConverter implements Converter<IngredientDto, Ingredient> {

    private final Converter<UnitOfMeasureDto, UnitOfMeasure> unitOfMeasureDtoUnitOfMeasureConverter;

    @Nullable
    @Synchronized
    @Override
    public Ingredient convert(IngredientDto ingredientDto) {
        if (ingredientDto == null) return null;

        UnitOfMeasure unitOfMeasure = null;
        if (ingredientDto.getUnitOfMeasure() != null) {
            unitOfMeasure = unitOfMeasureDtoUnitOfMeasureConverter.convert(ingredientDto.getUnitOfMeasure());
        }

        return Ingredient.builder().id(ingredientDto.getId()).
                amount(ingredientDto.getAmount()).description(ingredientDto.getDescription()).
                unitOfMeasure(unitOfMeasure).build();
    }
}
