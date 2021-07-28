package com.sudipta.mynote.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.sudipta.mynote.R;
import com.sudipta.mynote.db.DatabaseClient;
import com.sudipta.mynote.db.Note;

public class ShowNote extends AppCompatActivity {

    private TextView noteTitle;
    private TextView noteBody;
    private TextView noteTime;
    private ImageView deleteIVB;
    private ImageView noteImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);
//        getSupportActionBar().hide();
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);  //set full screen;
        getSupportActionBar().setElevation(0);

        noteTitle = findViewById(R.id.noteTitle_TV);
        noteBody = findViewById(R.id.noteBody_TV);
        noteTime = findViewById(R.id.time_TV);
        deleteIVB=findViewById(R.id.delete_IVB);
        noteImageView = findViewById(R.id.note_imageView);

//        Intent intent = getIntent();
//        String title = intent.getStringExtra("nTitle");
//        String body = intent.getStringExtra("nBody");

        final Note note = (Note) getIntent().getSerializableExtra("Rnote");

        loaders(note);

//        noteTitle.setText(title);
//        noteBody.setText(body);

        deleteIVB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShowNote.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteNote(note);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
            }
        });
    }

    public void loaders(Note note) {
        noteTitle.setText(note.getTitle());
        noteBody.setText(note.getNote());
        noteTime.setText(note.getDateTime());

        if (note.getImagePath() != null) {
            noteImageView.setImageBitmap(BitmapFactory.decodeFile(note.getImagePath()));
            noteImageView.setVisibility(View.VISIBLE);
        } else {
            noteImageView.setVisibility(View.GONE);
        }

    }

    private void deleteNote(Note note) {
        class DeleteNote extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .noteDao()
                        .deleteNote(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(ShowNote.this, MainActivity.class));
            }
        }

        DeleteNote dn = new DeleteNote();
        dn.execute();

    }

}