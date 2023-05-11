package com.luminate.crudattempt2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NoteRVAdapter extends RecyclerView.Adapter<NoteRVAdapter.ViewHolder> {
    private ArrayList<NoteRVModal> noteRVModalArrayList;
    private Context context;
    private NoteClickInterface noteClickInterface;
    int lastPos = -1;

    public NoteRVAdapter(ArrayList<NoteRVModal> noteRVModalArrayList, Context context, NoteClickInterface noteClickInterface) {
        this.noteRVModalArrayList = noteRVModalArrayList;
        this.context = context;
        this.noteClickInterface = noteClickInterface;
    }

    @NonNull
    @Override
    public NoteRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.note_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteRVAdapter.ViewHolder holder, int position) {
        NoteRVModal noteRVModal = noteRVModalArrayList.get(position);
        holder.noteName.setText(noteRVModal.getNoteName());
        Picasso.get().load(noteRVModal.getNoteImg()).into(holder.noteIV);
        setAnimation(holder.itemView, position);
        holder.noteIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteClickInterface.onNoteClick(position);
            }
        });
    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPos) {
            // on below line we are setting animation.
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return noteRVModalArrayList.size();
    }

    public interface NoteClickInterface {
        void onNoteClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView noteIV;
        private TextView noteName;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            noteIV = itemView.findViewById(R.id.idIVNote);
            noteName = itemView.findViewById(R.id.idTVCOurseName);
        }
    }
}
