package com.example.notes.date;

public interface NotesSource {
    Note getNoteData(int position);
    int size();

    void deleteNote(int position);
    void updateNote(int position, Note note);
    void addNote(Note note);
    void clearNote();
}
