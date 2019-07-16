package com.shiju.recipe.services;

import com.shiju.recipe.domain.UnitOfMeasure;
import com.shiju.recipe.dto.UnitOfMeasureDto;
import com.shiju.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;

    private final Converter<UnitOfMeasure, UnitOfMeasureDto> converter;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, Converter<UnitOfMeasure, UnitOfMeasureDto> converter) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.converter = converter;
    }

    @Override
    public Set<UnitOfMeasureDto> findAll() {
        Set<UnitOfMeasureDto> unitOfMeasureDtos = new HashSet<>();
        for(UnitOfMeasure unitOfMeasure: unitOfMeasureRepository.findAll()) {
            unitOfMeasureDtos.add(converter.convert(unitOfMeasure));
        }
        return unitOfMeasureDtos;
    }
}
