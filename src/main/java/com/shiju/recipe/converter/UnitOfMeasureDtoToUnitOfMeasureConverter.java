package com.shiju.recipe.converter;

import com.shiju.recipe.domain.UnitOfMeasure;
import com.shiju.recipe.dto.UnitOfMeasureDto;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureDtoToUnitOfMeasureConverter implements Converter<UnitOfMeasureDto, UnitOfMeasure> {

    @Override
    @Nullable
    @Synchronized
    public UnitOfMeasure convert(UnitOfMeasureDto unitOfMeasureDto) {
        if (unitOfMeasureDto == null) return null;
        return UnitOfMeasure.builder().id(unitOfMeasureDto.getId()).description(unitOfMeasureDto.getDescription()).build();
    }
}
