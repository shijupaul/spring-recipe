package com.shiju.recipe.converter;

import com.shiju.recipe.domain.Ingredient;
import com.shiju.recipe.dto.IngredientDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IngredientToIngredientDtoConverterTest {

    @Autowired
    private Converter<Ingredient, IngredientDto> ingredientToIngredientDtoConverter;

    @Test
    public void dependencyCheck() {
        Assert.assertNotNull("Created IngredientToIngredientDtoConverter", ingredientToIngredientDtoConverter);
    }
}
