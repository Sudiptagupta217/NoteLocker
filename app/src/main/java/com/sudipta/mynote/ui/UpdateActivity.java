package com.sudipta.mynote.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sudipta.mynote.R;
import com.sudipta.mynote.db.DatabaseClient;
import com.sudipta.mynote.db.Note;

public class UpdateActivity extends AppCompatActivity {

    EditText titleEditText1;
    EditText noteEditText1;
    FloatingActionButton updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);  //set full screen;

        getSupportActionBar().setTitle("Update");

        titleEditText1 = findViewById(R.id.title_editText1);
        noteEditText1 = findViewById(R.id.note_editText1);
        updateBtn = findViewById(R.id.update_button);

//        Intent intent = getIntent();
//        String title = intent.getStringExtra("nTitle");
//        String body = intent.getStringExtra("nBody");

        final Note note = (Note) getIntent().getSerializableExtra("Rnote");

        loaders(note);

//        titleEditText1.setText(title);
//        noteEditText1.setText(body);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTask(note);

            }
        });
    }

    public void loaders(Note note) {
        titleEditText1.setText(note.getTitle());
        noteEditText1.setText(note.getNote());
    }

    private void updateTask(final Note note) {
        String nTitle = titleEditText1.getText().toString();
        String nBody = noteEditText1.getText().toString();

        if (nTitle.isEmpty()) {
            titleEditText1.setError("Title required");
            titleEditText1.requestFocus();
            return;
        }

        if (nBody.isEmpty()) {
            noteEditText1.setError("Notes required");
            noteEditText1.requestFocus();
            return;
        }

        class UpdateTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                note.setTitle(nTitle);
                note.setNote(nBody);
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .noteDao()
                        .updateNote(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateActivity.this, MainActivity.class));
            }
        }

        UpdateTask ut = new UpdateTask();
        ut.execute();
    }
}