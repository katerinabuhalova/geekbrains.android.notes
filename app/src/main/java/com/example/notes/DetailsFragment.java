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
    private NotesSource data;
    private int position;
    private EditText editTextName;
    private EditText editTextDescription;
    private EditText editTextDate;
    private Button saveButton;
    private Note note;

    public static DetailsFragment newInstance(NotesSource data, int position) {
        DetailsFragment f = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putSerializable("data", data);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        position = getArguments().getInt("position", 0);
        data = (NotesSource) getArguments().getSerializable("data");
        editTextName = view.findViewById(R.id.name);
        editTextDescription = view.findViewById(R.id.description);
        editTextDate = view.findViewById(R.id.date);
        saveButton = view.findViewById(R.id.button_save);

        note = data.getNoteData(position);
        editTextName.setText(note.getNameNote());
        editTextDescription.setText(note.getDescriptionNote());
        editTextDate.setText(note.getDate());
        saveButton.setOnClickListener(v -> {
            saveNote();
        });


        return view;
    }

    private void saveNote() {
      note.setNameNote(editTextName.getText().toString());
      note.setDescriptionNote(editTextDescription.getText().toString());
      note.setDate(editTextDate.getText().toString());
        data.updateNote(position, note);
    }
}