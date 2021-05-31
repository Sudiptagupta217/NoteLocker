package com.sudipta.mynote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sudipta.mynote.db.DatabaseClient;
import com.sudipta.mynote.db.Note;
import com.sudipta.mynote.ui.MainActivity;

public class UpdateActivity extends AppCompatActivity {

    EditText titleEditText;
    EditText noteEditText;
    FloatingActionButton updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        getSupportActionBar().setTitle("Update");

        titleEditText = findViewById(R.id.title_editText);
        noteEditText = findViewById(R.id.note_editText);
        updateBtn = findViewById(R.id.update_button);


        Intent intent = getIntent();
        String title = intent.getStringExtra("nTitle");
        String body = intent.getStringExtra("nBody");

        final Note task = (Note) getIntent().getSerializableExtra(title+body);

//        titleEditText.setText(title);
//        noteEditText.setText(body);

        loadtask(task);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateActivity.this, "ok", Toast.LENGTH_SHORT).show();
                updateTask(task);
            }
        });
    }

    public void loadtask(Note task){
        titleEditText.setText(task.getTitle());
        noteEditText.setText(task.getNote());
    }

    private void updateTask(Note task) {
        String nTitle = titleEditText.getText().toString().trim();
        String nBody = noteEditText.getText().toString().trim();

        if (nTitle.isEmpty()) {
            titleEditText.setError("notes required");
            titleEditText.requestFocus();
            return;
        }

        if (nBody.isEmpty()) {
            noteEditText.setError("notes required");
            noteEditText.requestFocus();
            return;
        }

        class UpdateTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                task.setTitle(nTitle);
                task.setNote(nBody);
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .noteDao()
                        .updateNote(task);
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