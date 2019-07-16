package com.shiju.recipe.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotesDto {
    private Long id;
    private String recipeNotes;
}
