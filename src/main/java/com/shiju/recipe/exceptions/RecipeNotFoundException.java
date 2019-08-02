package com.shiju.recipe.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException() {

    }

    public RecipeNotFoundException(String message) {
        super(message);
    }

    public RecipeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
