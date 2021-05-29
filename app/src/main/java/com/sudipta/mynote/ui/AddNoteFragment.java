package com.sudipta.mynote.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sudipta.mynote.R;
import com.sudipta.mynote.db.DatabaseClient;
import com.sudipta.mynote.db.Note;
import com.sudipta.mynote.db.NoteDatabase;

public class AddNoteFragment extends Fragment {

    EditText titleEditText;
    EditText noteEditText;
    FloatingActionButton saveBtn;

    NoteDatabase nodeDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_note, container, false);

        titleEditText = view.findViewById(R.id.title_editText);
        noteEditText = view.findViewById(R.id.note_editText);
        saveBtn = view.findViewById(R.id.save_button);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpDb();
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String noteTitle = titleEditText.getText().toString().trim();
                String noteBody = noteEditText.getText().toString().trim();

                if (!noteTitle.isEmpty()) {
                    if (!noteBody.isEmpty()) {
                        Note note = new Note(noteTitle, noteBody);
                        nodeDatabase.getNoteDao().addNote(note);
                        Toast.makeText(getContext(), "Note saved", Toast.LENGTH_LONG).show();
                    }else {
                        noteEditText.setError("notes required");
                    }
                } else {
                    titleEditText.setError("title required");
                }
            }
        });
    }

    private void setUpDb() {
        nodeDatabase = DatabaseClient.databaseClient(getContext());
    }
}