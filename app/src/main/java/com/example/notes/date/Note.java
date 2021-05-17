package com.example.notes.date;

public class Note {
    private String id;
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

    public void setNameNote(String nameNote) {
        this.nameNote = nameNote;
    }

    public String getDescriptionNote() {
        return descriptionNote;
    }

    public void setDescriptionNote(String descriptionNote) {
        this.descriptionNote = descriptionNote;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
