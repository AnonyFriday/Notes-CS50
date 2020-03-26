package edu.harvard.cs50.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private NotesAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static NotesDatabase database;


    @Override
    protected void onPostResume() {
        super.onPostResume();
        mAdapter.reload();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = Room
                .databaseBuilder(getApplicationContext(), NotesDatabase.class, "notes")
                .allowMainThreadQueries()
                .build();

        mRecyclerView = findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);

        mAdapter = new NotesAdapter();
        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(mRecyclerView);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);
        // help you to scroll to from beginning to the end of the list
        // combine with the reverseLayout(true)

        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);

        FloatingActionButton floatingActionButton = findViewById(R.id.add_note_button);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.noteDao().create();
                mAdapter.reload();

                Intent intent = new Intent(v.getContext(), NoteActivity.class);
                Note note = mAdapter.notes.get(mAdapter.notes.size() - 1);
                intent.putExtra("id", note.id);
                intent.putExtra("contents", note.contents);
                startActivity(intent);
            }
        });

        mAdapter.reload();

        /*ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                mAdapter.delete(position);
                mAdapter.notifyDataSetChanged();
            }
        });
        helper.attachToRecyclerView(mRecyclerView);
*/
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            mAdapter.removeItem(viewHolder.getAdapterPosition());
            mAdapter.notifyDataSetChanged();
        }

    };


}
