package edu.harvard.cs50.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.os.Bundle;
import android.widget.EditText;

public class NoteActivity extends AppCompatActivity {
    private EditText mEditText;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mEditText = findViewById(R.id.note_editText);
        String contents = getIntent().getStringExtra("contents");
        id = getIntent().getIntExtra("id", 0);
        mEditText.setText(contents);

    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.database.noteDao().save(mEditText.getText().toString(), id);
    }



}
