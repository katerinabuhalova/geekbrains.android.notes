package com.example.notes.date;

public interface NotesSource {
    Note getNoteData(int position);
    int size();
}
