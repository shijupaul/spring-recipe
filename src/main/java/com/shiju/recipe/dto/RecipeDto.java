package com.shiju.recipe.dto;

import com.shiju.recipe.domain.Difficulty;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDto {
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String direction;
    private NotesDto notes;
    private Set<IngredientDto> ingredients;
    private Difficulty difficulty;
    private Set<CategoryDto> categories;
    private String image;
}
