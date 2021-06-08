package com.sudipta.mynote.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sudipta.mynote.R;
import com.sudipta.mynote.db.DatabaseClient;
import com.sudipta.mynote.db.Note;

public class AddNoteFragment extends Fragment {

    EditText titleEditText;
    EditText noteEditText;
    FloatingActionButton saveBtn;

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
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String noteTitle = titleEditText.getText().toString().trim();
                String noteBody = noteEditText.getText().toString().trim();

                if (!noteTitle.isEmpty()) {
                    if (!noteBody.isEmpty()) {

                        saveNote();


                    } else {
                        noteEditText.setError("Notes required");
                        noteEditText.requestFocus();
                    }
                } else {
                    titleEditText.setError("Title required");
                    titleEditText.requestFocus();
                }
            }
        });
    }

    private void saveNote() {
        class SaveNote extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                Note note = new Note(titleEditText.getText().toString(), noteEditText.getText().toString(),null,null);

                //adding to database
                DatabaseClient.getInstance(getContext()).getAppDatabase().noteDao().addNote(note);

                return null;
            }


            @Override
            protected void onPostExecute(Void v) {
                super.onPostExecute(v);
                Intent mainIntent = new Intent(getActivity(), MainActivity.class);
//                getActivity().finish();
                startActivity(mainIntent);
                Toast.makeText(getContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveNote sn = new SaveNote();
        sn.execute();
    }
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_righr,R.anim.slideout_from_left);
        fragmentTransaction.replace(R.id.fragment,fragment);
        fragmentTransaction.commit();
    }

}