package com.example.notes.date;

public class Note {
    private String nameNote;
    private String descriptionNote;
    private String date;

    public Note(String nameNote, String descriptionNote, String date ) {
        this.nameNote = nameNote;
        this.descriptionNote = descriptionNote;
        this.date = date;
    }

    public String getNameNote() {
        return nameNote;
    }

    public String getDescriptionNote() {
        return descriptionNote;
    }

    public String getDate() {
        return date;
    }
}
