package com.shiju.recipe.converter;

import com.shiju.recipe.domain.UnitOfMeasure;
import com.shiju.recipe.dto.UnitOfMeasureDto;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureToUnitOfMeasureDtoConverter implements Converter<UnitOfMeasure, UnitOfMeasureDto> {

    @Nullable
    @Synchronized
    @Override
    public UnitOfMeasureDto convert(UnitOfMeasure unitOfMeasure) {
        if (unitOfMeasure == null) return null;
        return UnitOfMeasureDto.builder().id(unitOfMeasure.getId()).description(unitOfMeasure.getDescription()).build();
    }
}
