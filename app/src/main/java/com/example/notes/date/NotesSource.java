package com.example.notes.date;

import java.io.Serializable;

public interface NotesSource extends Serializable {

    NotesSource init(NotesSourceResponse notesSourceResponse);
    Note getNoteData(int position);

    int size();

    void deleteNote(int position);
    void updateNote(int position, Note note);
    void addNote(Note note);
    void clearNote();
}
