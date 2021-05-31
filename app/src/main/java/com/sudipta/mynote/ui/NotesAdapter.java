package com.sudipta.mynote.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sudipta.mynote.R;
import com.sudipta.mynote.UpdateActivity;
import com.sudipta.mynote.db.Note;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.navigation.NavDeepLink;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Update;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private Context context;
    private List<Note> notes;

    public NotesAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_layout, viewGroup, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder viewHolder, int position) {

        String title = notes.get(position).getTitle();
        String body =notes.get(position).getNote();

        viewHolder.setData(title,body);

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTv;
        private TextView bodyTv;
        private ImageView editImageBtn;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.textView_title);
            bodyTv = itemView.findViewById(R.id.textView_note);
            editImageBtn= itemView.findViewById(R.id.edit_imageView);

        }

        public void setData(String title, String body) {
            titleTv.setText(title);
            bodyTv.setText(body);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ShowNote.class);
                    intent.putExtra("nTitle",title);
                    intent.putExtra("nBody",body);
                    context.startActivity(intent);
                }
            });
            editImageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, UpdateActivity.class);
                    intent.putExtra("nTitle",title);
                    intent.putExtra("nBody",body);
                    context.startActivity(intent);
                }
            });
        }
    }
}
