package com.shiju.recipe.converter;

import com.shiju.recipe.domain.Notes;
import com.shiju.recipe.dto.NotesDto;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class NotesDtoToNotesConverter implements Converter<NotesDto, Notes> {

    @Override
    @Synchronized
    @Nullable
    public Notes convert(NotesDto notesDto) {
        if (notesDto == null) return null;
        return Notes.builder().id(notesDto.getId()).recipeNotes(notesDto.getRecipeNotes()).build();
    }
}
