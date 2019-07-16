package com.shiju.recipe.converter;

import com.shiju.recipe.domain.Notes;
import com.shiju.recipe.dto.NotesDto;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesDtoConverter implements Converter<Notes, NotesDto> {

    @Override
    @Nullable
    @Synchronized
    public NotesDto convert(Notes notes) {
        if (notes == null) return null;
        return NotesDto.builder().id(notes.getId()).recipeNotes(notes.getRecipeNotes()).build();
    }
}
