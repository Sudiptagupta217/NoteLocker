package com.sudipta.mynote.ui;

import android.content.Intent;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sudipta.mynote.R;
import com.sudipta.mynote.db.DatabaseClient;
import com.sudipta.mynote.db.Note;

public class AddNoteFragment extends Fragment {

    private EditText titleEditText;
    private EditText noteEditText;
    private FloatingActionButton saveBtn;
   // private LinearLayout layoutbotm;
    private View viewtitleIndicator;

//    ImageView imageColor1;
//    ImageView imageColor2;
//    ImageView imageColor3;
//    ImageView imageColor4;
//    ImageView imageColor5;
//    ImageView imageColor6;
//
//    View viewColor1, viewColor2, viewColor3, viewColor4, viewColor5, viewColor6;

    private String selectedNoteColor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_note, container, false);
        titleEditText = view.findViewById(R.id.title_editText);
        noteEditText = view.findViewById(R.id.note_editText);
        saveBtn = view.findViewById(R.id.save_button);
        viewtitleIndicator = view.findViewById(R.id.viewtitleIndicator);
       // layoutbotm = view.findViewById(R.id.botmbarlayout);

//        imageColor1 = view.findViewById(R.id.imageColor1);
//        imageColor2 = view.findViewById(R.id.imageColor2);
//        imageColor3 = view.findViewById(R.id.imageColor3);
//        imageColor4 = view.findViewById(R.id.imageColor4);
//        imageColor5 = view.findViewById(R.id.imageColor5);
//        imageColor6 = view.findViewById(R.id.imageColor6);
//
//        viewColor1 = view.findViewById(R.id.viewColor1);
//        viewColor2 = view.findViewById(R.id.viewColor2);
//        viewColor3 = view.findViewById(R.id.viewColor3);
//        viewColor4 = view.findViewById(R.id.viewColor4);
//        viewColor5 = view.findViewById(R.id.viewColor5);
//        viewColor6 = view.findViewById(R.id.viewColor6);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        selectedNoteColor = "#333333";

        bottomsheetBtn();
       // setTitleIndicatorColor();

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

                Note note = new Note(titleEditText.getText().toString(), noteEditText.getText().toString(),null,selectedNoteColor);

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
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_righr, R.anim.slideout_from_left);
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }

    private void bottomsheetBtn() {
         final LinearLayout layoutbotm = getActivity().findViewById(R.id.botmbarlayout);
        final BottomSheetBehavior<LinearLayout> bottomSheetBehavior = BottomSheetBehavior.from(layoutbotm);
        layoutbotm.findViewById(R.id.textbt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

//
        final ImageView imageColor1 = layoutbotm.findViewById(R.id.imageColor1);
        final ImageView imageColor2 = layoutbotm.findViewById(R.id.imageColor2);
        final ImageView imageColor3 = layoutbotm.findViewById(R.id.imageColor3);
        final ImageView imageColor4 = layoutbotm.findViewById(R.id.imageColor4);
        final ImageView imageColor5 = layoutbotm.findViewById(R.id.imageColor5);
        final ImageView imageColor6 = layoutbotm.findViewById(R.id.imageColor6);

       layoutbotm.findViewById(R.id.viewColor1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedNoteColor = "#FFBB86FC";
                imageColor1.setImageResource(R.drawable.ic_baseline_done_24);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(0);
                imageColor6.setImageResource(0);
             //   setTitleIndicatorColor();
            }
        });

        layoutbotm.findViewById(R.id.viewColor2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedNoteColor = "#FFEB3B";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(R.drawable.ic_baseline_done_24);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(0);
                imageColor6.setImageResource(0);
               // setTitleIndicatorColor();
            }
        });

        layoutbotm.findViewById(R.id.viewColor3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedNoteColor = "#ae3b76";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(R.drawable.ic_baseline_done_24);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(0);
                imageColor6.setImageResource(0);
              //  setTitleIndicatorColor();
            }
        });

        layoutbotm.findViewById(R.id.viewColor4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedNoteColor = "#8BC34A";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(R.drawable.ic_baseline_done_24);
                imageColor5.setImageResource(0);
                imageColor6.setImageResource(0);
               // setTitleIndicatorColor();
            }
        });

        layoutbotm.findViewById(R.id.viewColor5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedNoteColor = "#ff7746";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(R.drawable.ic_baseline_done_24);
                imageColor6.setImageResource(0);
                //setTitleIndicatorColor();
            }
        });

        layoutbotm.findViewById(R.id.viewColor6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedNoteColor = "#0aebaf";
                imageColor1.setImageResource(0);
                imageColor2.setImageResource(0);
                imageColor3.setImageResource(0);
                imageColor4.setImageResource(0);
                imageColor5.setImageResource(0);
                imageColor6.setImageResource(R.drawable.ic_baseline_done_24);
               // setTitleIndicatorColor();
            }
        });
    }

//    private void setTitleIndicatorColor() {
//        GradientDrawable gradientDrawable = (GradientDrawable) viewtitleIndicator.getBackground();
//        gradientDrawable.setColor(Color.parseColor(selectedNoteColor));
//    }
}