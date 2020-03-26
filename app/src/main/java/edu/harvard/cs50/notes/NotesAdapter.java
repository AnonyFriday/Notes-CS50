package edu.harvard.cs50.notes;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_row, parent, false);

        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note current = notes.get(position);
        holder.textView.setText(current.contents);
        holder.containerView.setTag(current);
    }

    public List<Note> notes = new ArrayList<>();


    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        LinearLayout containerView;
        TextView textView;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            containerView = itemView.findViewById(R.id.note_row);
            textView = itemView.findViewById(R.id.note_row_text);

            containerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Note current = (Note) containerView.getTag();
                    Intent intent = new Intent(v.getContext(),NoteActivity.class);
                    intent.putExtra("id", current.id);
                    intent.putExtra("contents", current.contents);

                    v.getContext().startActivity(intent);
                }
            });
        }
    }

    public void reload(){
        notes = MainActivity.database.noteDao().getAllNotes(); // call from the note Dao, run the select query and give ti back to us
        notifyDataSetChanged();
    }

    public void removeItem(int id)
    {
        notes.remove(id);
        MainActivity.database.noteDao().delete(id);
        notifyItemRemoved(id);
    }



}
