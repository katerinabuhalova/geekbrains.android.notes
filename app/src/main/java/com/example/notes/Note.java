package com.example.notes;

public class Note {
    private String nameNote;
    private String descriptionNote;
    private int date;

    public void Note() {
        String nameNote = this.nameNote;
        String descriptionNote = this.descriptionNote;
        int date = this.date;
    }

    public String getNameNote() {
        return nameNote;
    }

    public String getDescriptionNote() {
        return descriptionNote;
    }

    public int getDate() {
        return date;
    }
}
