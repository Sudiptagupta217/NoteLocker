package com.sudipta.mynote.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.sudipta.mynote.R;

public class ShowNote extends AppCompatActivity {

    private TextView noteTitle;
    private TextView noteBody;
    private TextView noteTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_note);
        getSupportActionBar().hide();

        noteTitle = findViewById(R.id.noteTitle_TV);
        noteBody = findViewById(R.id.noteBody_TV);
        noteTime = findViewById(R.id.time_TV);

        Intent intent = getIntent();
        String title = intent.getStringExtra("nTitle");
        String body = intent.getStringExtra("nBody");

        noteTitle.setText(title);
        noteBody.setText(body);
    }
}