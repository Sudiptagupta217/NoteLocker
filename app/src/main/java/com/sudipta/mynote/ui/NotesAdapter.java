package com.sudipta.mynote.ui;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sudipta.mynote.R;
import com.sudipta.mynote.db.Note;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private Context context;
    private List<Note> notes;
    private Timer timer;
    private List<Note> notesSource;

    public NotesAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
        notesSource =notes;
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
        String body = notes.get(position).getNote();
        String color = notes.get(position).getColor();
        String imagepath = notes.get(position).getImagePath();
        String dateTime = notes.get(position).getDateTime();

        viewHolder.setData(title, body, color, imagepath,dateTime);

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTv;
        private TextView bodyTv;
        private ImageView editImageBtn;
        ConstraintLayout notelayout;
        ImageView imageNote;
        TextView textDateTime;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.textView_title);
            bodyTv = itemView.findViewById(R.id.textView_note);
            editImageBtn = itemView.findViewById(R.id.edit_imageView);
            notelayout = itemView.findViewById(R.id.notelayout);
            imageNote = itemView.findViewById(R.id.imagenote);
            textDateTime= itemView.findViewById(R.id.textDateTime);

        }

        public void setData(String title, String body, String color, String imagepath,String DateTime) {
            titleTv.setText(title);
            bodyTv.setText(body);
            textDateTime.setText(DateTime);
            // GradientDrawable gradientDrawable= (GradientDrawable) notelayout.getBackground();
            //gradientDrawable.setColor(Color.parseColor(color));
            //notelayout.setBackground(color);
            if (imagepath != null) {
                imageNote.setImageBitmap(BitmapFactory.decodeFile(imagepath));
                imageNote.setVisibility(View.VISIBLE);
            } else {
                imageNote.setVisibility(View.GONE);
            }



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(context, ShowNote.class);
//                    intent.putExtra("nTitle",title);
//                    intent.putExtra("nBody",body);
//                    context.startActivity(intent);

                    Note note = notes.get(getAbsoluteAdapterPosition());

                    Intent intent = new Intent(context, ShowNote.class);
                    intent.putExtra("Rnote", note);
                    Pair[] pairs = new Pair[2];
                    pairs[0] = new Pair<View, String>(titleTv, "Title");
                    pairs[1] = new Pair<View, String>(bodyTv, "Body");

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context, pairs);

                    //startActivity(intent,options.toBundle());
                    context.startActivity(intent,options.toBundle());
                }
            });
            editImageBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Note note = notes.get(getAbsoluteAdapterPosition());

                    Intent intent = new Intent(context, UpdateActivity.class);
                    intent.putExtra("Rnote", note);

                    context.startActivity(intent);
                }

            });
        }
    }

    public void searchNotes(final String searchkeyword) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (searchkeyword.trim().isEmpty()) {
                    notes = notesSource;
                } else {
                    ArrayList<Note> temp = new ArrayList<>();
                    for (Note note : notesSource) {
                        if (note.getTitle().toLowerCase().contains(searchkeyword.toLowerCase())
                                || note.getNote().toLowerCase().contains(searchkeyword.toLowerCase())) {
                            temp.add(note);
                        }
                    }
                    notes = temp;
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        }, 500);
    }
    public void cancelTimer(){
        if (timer!=null){
            timer.cancel();
        }
    }
}
