package com.shiju.recipe.converter;

import com.shiju.recipe.domain.Ingredient;
import com.shiju.recipe.dto.IngredientDto;
import com.shiju.recipe.dto.UnitOfMeasureDto;
import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class IngredientToIngredientDtoConverter implements Converter<Ingredient, IngredientDto> {

    private final UnitOfMeasureToUnitOfMeasureDtoConverter unitOfMeasureUnitOfMeasureDtoConverter;

    @Nullable
    @Synchronized
    @Override
    public IngredientDto convert(Ingredient ingredient) {
        if (ingredient == null) return null;

        UnitOfMeasureDto unitOfMeasureDto = null;
        if (ingredient.getUnitOfMeasure() != null) {
            unitOfMeasureDto = unitOfMeasureUnitOfMeasureDtoConverter.convert(ingredient.getUnitOfMeasure());
        }

        return IngredientDto.builder().amount(ingredient.getAmount()).
                id(ingredient.getId()).description(ingredient.getDescription()).
                unitOfMeasure(unitOfMeasureDto).
                build();
    }
}
