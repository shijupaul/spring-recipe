package com.shiju.recipe.services;

import com.shiju.recipe.domain.Recipe;
import com.shiju.recipe.repositories.RecipeRepository;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Transactional
    @Override
    public void storeImage(Long recipeId, MultipartFile file) throws Exception {
        Recipe recipe = recipeRepository.findById(recipeId).get();
        recipe.setImage(ArrayUtils.toObject(IOUtils.toByteArray(file.getInputStream())));
        recipeRepository.save(recipe);
    }
}
