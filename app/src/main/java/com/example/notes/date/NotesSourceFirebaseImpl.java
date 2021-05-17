package com.example.notes.date;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotesSourceFirebaseImpl implements NotesSource {

    private static final String NOTES_COLLECTION = "NOTES";
    private static final String TAG = "[NotesSourceFirebaseImpl]";

    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    private CollectionReference collection = store.collection(NOTES_COLLECTION);

    private List<Note> notes = new ArrayList<Note>();

    @SuppressLint("LongLogTag")
    @Override
    public NotesSource init(final NotesSourceResponse notesSourceResponse) {
        collection.orderBy(NoteMapping.Fields.DATE, Query.Direction.DESCENDING).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        notes = new ArrayList<Note>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> doc = document.getData();
                            String id = document.getId();
                            Note note = NoteMapping.toNote(id, doc);
                            notes.add(note);
                        }
                        Log.d(TAG, "success " + notes.size() + " qnt");
                        notesSourceResponse.initialized(this);
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "get failed with ", e);
                    }
                });
        return this;
    }

    @Override
    public Note getNoteData(int position) {
        return notes.get(position);
    }

    @Override
    public int size() {
        if (notes == null) {
            return 0;
        }
        return notes.size();
    }

    @Override
    public void deleteNote(int position) {
        collection.document(notes.get(position).getId()).delete();
        notes.remove(position);
    }

    @Override
    public void updateNote(int position, Note note) {
        String id = note.getId();
        collection.document(id).set(NoteMapping.toDocument(note));
        notes.set(position, note);
    }

    @Override
    public void addNote(final Note note) {
        collection.add(NoteMapping.toDocument(note)).addOnSuccessListener(documentReference -> note.setId(documentReference.getId()));
        notes.add(note);
    }

    @Override
    public void clearNote() {
        for (Note note : notes) {
            collection.document(note.getId()).delete();
        }
        notes = new ArrayList<Note>();
    }
}

