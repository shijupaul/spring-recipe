package com.shiju.recipe.converter;

import com.shiju.recipe.domain.Category;
import com.shiju.recipe.dto.CategoryDto;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryDtoConverter implements Converter<Category, CategoryDto> {

    @Nullable
    @Synchronized
    @Override
    public CategoryDto convert(Category category) {
        if (category == null) return null;
        return CategoryDto.builder().id(category.getId()).name(category.getName()).build();
    }
}
