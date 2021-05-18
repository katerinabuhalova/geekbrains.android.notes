package com.example.notes.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.DetailsFragment;
import com.example.notes.MainActivity;
import com.example.notes.R;
import com.example.notes.date.Note;
import com.example.notes.date.NotesSource;
import com.example.notes.date.NotesSourceFirebaseImpl;
import com.example.notes.date.NotesSourceImpl;
import com.example.notes.date.NotesSourceResponse;

import java.util.Calendar;

public class NotesCollectionFragment  extends Fragment {

    private NotesSource data;
    private NotesCollectionAdapter adapter;
    private RecyclerView recyclerView;

    public static NotesCollectionFragment newInstance() {
        return new NotesCollectionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_collection, container, false);
        initView(view);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.notes_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                data.addNote(new Note("Note" + data.size(),
                        "description" + data.size(),
                        Calendar.getInstance().getTime().toString()));
                adapter.notifyItemInserted(data.size() - 1);
                recyclerView.scrollToPosition(data.size() - 1);
                return true;
            case R.id.action_clear:
                data.clearNote();
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_lines);
        initRecyclerView();
        data = new NotesSourceFirebaseImpl().init(notes -> adapter.notifyDataSetChanged());
        adapter.setDataSource(data);
    }

    private void initRecyclerView() {

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator));
        recyclerView.addItemDecoration(itemDecoration);

        adapter = new NotesCollectionAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.SetOnItemClickListener((view, position) ->
        {
            openDetails(data, position);
        });
    }

    private void openDetails(NotesSource data, int position) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DetailsFragment detailsFragment = DetailsFragment.newInstance(data, position);
        fragmentTransaction.replace(R.id.fragment_container, detailsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = adapter.getMenuPosition();
        switch (item.getItemId()) {
            case R.id.action_update:
                data.updateNote(position,
                        new Note("Updated note" + position,
                                data.getNoteData(position).getDescriptionNote(),
                                data.getNoteData(position).getDate()));
                adapter.notifyItemChanged(position);
                return true;
            case R.id.action_delete:
                //  data.deleteNote(position);
                // adapter.notifyItemRemoved(position);
                snowDeleteDialog(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    public void snowDeleteDialog( int position) {
        // Создаём билдер и передаём контекст приложения
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // В билдере указываем заголовок окна. Можно указывать как ресурс,
        // так и строку
        builder.setTitle(R.string.exclamation)
                // Указываем сообщение в окне. Также есть вариант со
                // строковым параметром
                .setMessage(R.string.press_button)
                // Можно указать и пиктограмму
                .setIcon(R.mipmap.ic_launcher_round)
                // Из этого окна нельзя выйти кнопкой Back
                .setCancelable(false)
                // Устанавливаем кнопку. Название кнопки также можно
                // задавать строкой
                .setNegativeButton(R.string.button1,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        })
                .setPositiveButton(R.string.button,
                        // Ставим слушатель, нажатие будем обрабатывать
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getActivity(), "Заметка удалена", Toast.LENGTH_SHORT).show();
                                data.deleteNote(position);
                                adapter.notifyItemRemoved(position);
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
