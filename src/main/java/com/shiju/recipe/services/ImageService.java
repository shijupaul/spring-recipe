package com.shiju.recipe.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void storeImage(Long recipeId, MultipartFile file) throws Exception;
}
