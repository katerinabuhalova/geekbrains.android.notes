package com.example.notes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.date.NotesSource;
import com.example.notes.date.NotesSourceImpl;

public class NotesCollectionFragment  extends Fragment {

    public static NotesCollectionFragment newInstance() {
        return new NotesCollectionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes_collection, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_lines);
        NotesSource data = new NotesSourceImpl(getResources()).init();
        initRecyclerView(recyclerView, data);
        return view;
    }

    private void initRecyclerView(RecyclerView recyclerView, NotesSource data) {

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator));
        recyclerView.addItemDecoration(itemDecoration);

        NotesCollectionAdapter adapter = new NotesCollectionAdapter(data);
        recyclerView.setAdapter(adapter);
        adapter.SetOnItemClickListener((view, position) ->
                Toast.makeText(getContext(), String.format("Позиция - %d", position),
                        Toast.LENGTH_SHORT).show());
    }
}
