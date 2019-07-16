package com.shiju.recipe.converter;

import com.shiju.recipe.domain.Category;
import com.shiju.recipe.dto.CategoryDto;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class CategoryDtoToCategoryConverter implements Converter<CategoryDto, Category> {

    @Override
    @Nullable
    @Synchronized
    public Category convert(CategoryDto categoryDto) {
        if (categoryDto == null) return null;
        return Category.builder().id(categoryDto.getId()).name(categoryDto.getName()).recipes(new HashSet()).build();
    }
}
