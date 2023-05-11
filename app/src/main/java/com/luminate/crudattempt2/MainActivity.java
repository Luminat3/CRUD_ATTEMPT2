package com.luminate.crudattempt2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.security.PrivateKey;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NoteRVAdapter.NoteClickInterface {

    private FloatingActionButton addNoteFB;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private RecyclerView noteRV;
    FirebaseAuth mAuth;
    private ProgressBar loadingPB;
    private ArrayList<NoteRVModal> noteRVModalArrayList;
    private NoteRVAdapter noteRVAdapter;
    private RelativeLayout homeRL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteRV = findViewById(R.id.idRVNote);
        homeRL = findViewById(R.id.idRLHome);
        loadingPB = findViewById(R.id.idPBLoading);
        addNoteFB = findViewById(R.id.idFABAddNote);
        firebaseDatabase = FirebaseDatabase.getInstance();
        noteRVModalArrayList = new ArrayList<>();
        databaseReference = firebaseDatabase.getReference("Note");

        addNoteFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivity(i);
            }
        });

        noteRVAdapter = new NoteRVAdapter(noteRVModalArrayList, this, this::onNoteClick);
        noteRV.setLayoutManager(new LinearLayoutManager(this));
        noteRV.setAdapter(noteRVAdapter);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("note");

        myRef.setValue("Hello, World!");





        getNote();
    }

        private void getNote()
        {
            noteRVModalArrayList.clear();
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    noteRVModalArrayList.add(snapshot.getValue(NoteRVModal.class));
                    noteRVAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    loadingPB.setVisibility(View.GONE);
                    noteRVAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    noteRVAdapter.notifyDataSetChanged();
                    loadingPB.setVisibility(View.GONE);
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    noteRVAdapter.notifyDataSetChanged();
                    loadingPB.setVisibility(View.GONE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


    @Override
    public void onNoteClick(int position) {
        displayBottomSheet(noteRVModalArrayList.get(position));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void displayBottomSheet(NoteRVModal modal)
    {
        final BottomSheetDialog bottomSheetTeacherDialog =
                new BottomSheetDialog(this, R.style.BottomSheetDialogTheme);

        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_note_layout, homeRL);
        bottomSheetTeacherDialog.setContentView(layout);
        bottomSheetTeacherDialog.setCancelable(false);
        bottomSheetTeacherDialog.setCanceledOnTouchOutside(true);
        bottomSheetTeacherDialog.show();

        TextView noteNameTV = findViewById(R.id.idTVNoteName);
        TextView noteDescTV = findViewById(R.id.idTVnoteDesc);
        ImageView noteIV = layout.findViewById(R.id.idIVNote);
        noteNameTV.setText(modal.getNoteName());
        noteDescTV.setText(modal.getNoteDescription());
        Picasso.get().load(modal.getNoteImg()).into(noteIV);
        Button viewBTN = findViewById(R.id.idBtnVIewDetails);
        Button editBTN = findViewById(R.id.idBtnEditNote);

        editBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, EditNoteActivity.class);
                i.putExtra("note", modal);
                startActivity(i);
            }
        });

        viewBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are navigating to browser
                // for displaying course details from its url
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(modal.getNoteLink()));
                startActivity(i);
            }
        });
    }
}