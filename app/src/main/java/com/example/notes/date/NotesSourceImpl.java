package com.example.notes.date;

import android.content.res.Resources;

import com.example.notes.R;

import java.util.ArrayList;
import java.util.List;

public class NotesSourceImpl implements NotesSource {

    private List<Note> dataSource;
    private Resources resources;

    public NotesSourceImpl(Resources resources) {
        dataSource = new ArrayList<>(3);
        this.resources = resources;
    }

    public NotesSourceImpl init() {
        String[] notes = resources.getStringArray(R.array.notes);
        String[] descriptions = resources.getStringArray(R.array.descriptions);
        String[] dates = resources.getStringArray(R.array.date);
        for (int i = 0; i < descriptions.length; i++) {
            dataSource.add(new Note(notes[i], descriptions[i], dates[i]));
        }
        return this;
    }

    @Override
    public Note getNoteData(int position) {
        return dataSource.get(position);
    }

    @Override
    public int size() {
        return dataSource.size();
    }
}
