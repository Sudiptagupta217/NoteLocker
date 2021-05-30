package com.sudipta.mynote.ui;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sudipta.mynote.R;
import com.sudipta.mynote.db.DatabaseClient;
import com.sudipta.mynote.db.Note;

import java.util.List;


public class HomeFragment extends Fragment {
    FloatingActionButton addButton;

    // ListView listView;
    private RecyclerView recyclerView;
    NotesAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        addButton = view.findViewById(R.id.add_button);

        recyclerView = view.findViewById(R.id.recycler_view_note);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        getNotes();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = HomeFragmentDirections.actionAddNote();
                Navigation.findNavController(view).navigate(action);
            }

        });
    }


    private void getNotes() {
        class Getnotes extends AsyncTask<Void, Void, List<Note>> {

            @Override
            protected List<Note> doInBackground(Void... voids) {
                List<Note> noteList = DatabaseClient
                        .getInstance(getContext())
                        .getAppDatabase()
                        .noteDao()
                        .getAllNotes();
                return noteList;
            }

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                adapter = new NotesAdapter(getContext(), notes);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }

        Getnotes gn = new Getnotes();
        gn.execute();
    }
}