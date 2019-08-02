package com.shiju.recipe.dto;

import com.shiju.recipe.domain.Difficulty;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDto {
    private Long id;

    @NotBlank
    @Size(min=3, max=255)
    private String description;

    @Min(1)
    @Max(999)
    @NotNull
    private Integer prepTime;

    @Min(1)
    @Max(999)
    @NotNull
    private Integer cookTime;

    @Min(1)
    @Max(999)
    @NotNull
    private Integer servings;

    @NotBlank
    private String source;

    @URL
    private String url;
    private String direction;
    private NotesDto notes;
    private Set<IngredientDto> ingredients;
    private Difficulty difficulty;
    private Set<CategoryDto> categories;
    private String image;
}
