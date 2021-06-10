package com.sudipta.mynote.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sudipta.mynote.R;
import com.sudipta.mynote.db.Note;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

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
        String color = notes.get(position).getColor();

        viewHolder.setData(title,body,color);

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

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.textView_title);
            bodyTv = itemView.findViewById(R.id.textView_note);
            editImageBtn= itemView.findViewById(R.id.edit_imageView);
            notelayout=itemView.findViewById(R.id.notelayout);

        }

        public void setData(String title, String body , String color) {
            titleTv.setText(title);
            bodyTv.setText(body);
           // GradientDrawable gradientDrawable= (GradientDrawable) notelayout.getBackground();
            //gradientDrawable.setColor(Color.parseColor(color));
            //notelayout.setBackground(color);

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

                    context.startActivity(intent);
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
}
