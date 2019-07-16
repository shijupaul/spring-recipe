package com.shiju.recipe.converter;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class IngredientToIngredientDtoConverterTest {

    @Autowired
    private IngredientToIngredientDtoConverter ingredientToIngredientDtoConverter;

    @Test
    public void dependencyCheck() {
        Assert.assertNotNull("Created IngredientToIngredientDtoConverter", ingredientToIngredientDtoConverter);
    }
}
