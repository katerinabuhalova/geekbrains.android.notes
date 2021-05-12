package com.example.notes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.date.Note;
import com.example.notes.date.NotesSource;

public class NotesCollectionAdapter extends RecyclerView.Adapter<NotesCollectionAdapter.ViewHolder> {
    private final static String TAG = "SocialNetworkAdapter";
    private NotesSource dataSource;


    private OnItemClickListener itemClickListener;

    public NotesCollectionAdapter(NotesSource dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item_list_view, parent, false);
        // Здесь можно установить всякие параметры
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesCollectionAdapter.ViewHolder holder, int position) {
        holder.setData(dataSource.getNoteData(position));

    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public void SetOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private TextView description;
        private TextView date;
        private CardView noteView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
            noteView = itemView.findViewById(R.id.noteView);
            noteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        }

        public void setData(Note cardData) {
            name.setText(cardData.getNameNote());
            description.setText(cardData.getDescriptionNote());
            date.setText(cardData.getDate());
        }

    }
}
