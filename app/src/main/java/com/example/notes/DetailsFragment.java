package com.example.notes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.notes.date.Note;
import com.example.notes.date.NotesSource;


public class DetailsFragment extends Fragment {
    private final NotesSource data;
    private final int position;
    private EditText editTextName;
    private EditText editTextDescription;
    private DatePicker datePicker;
    private Button saveButton;

    public DetailsFragment(NotesSource data, int position) {

        this.data = data;
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        editTextName = view.findViewById(R.id.name);
        editTextDescription = view.findViewById(R.id.description);
        saveButton = view.findViewById(R.id.button_save);
        Note note = data.getNoteData(position);
        editTextName.setText(note.getNameNote());
        editTextDescription.setText(note.getDescriptionNote());
        saveButton.setOnClickListener(v -> {
            saveNote();
        });
        return view;
    }

    private void saveNote() {
        Note note = new Note(editTextName.getText().toString(), editTextDescription.getText().toString(), "01.01.2021");
        data.updateNote(position, note);
    }
}