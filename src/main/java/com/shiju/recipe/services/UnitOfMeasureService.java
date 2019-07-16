package com.shiju.recipe.services;

import com.shiju.recipe.dto.UnitOfMeasureDto;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureDto> findAll();
}
