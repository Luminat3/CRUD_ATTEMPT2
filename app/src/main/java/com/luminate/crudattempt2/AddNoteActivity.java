package com.luminate.crudattempt2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddNoteActivity extends AppCompatActivity {
    private Button addNoteBtn;
    private TextInputEditText NoteNameEdt,
            NoteDescEdt, NoteImgEdt, NoteLinkEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ProgressBar loadingPB;
    private String NoteID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        addNoteBtn = findViewById(R.id.idBtnAddNote);
        NoteNameEdt = findViewById(R.id.idEdtNoteName);
        NoteDescEdt = findViewById(R.id.idEdtNoteDescription);
        NoteImgEdt = findViewById(R.id.idEdtNoteImageLink);
        NoteLinkEdt = findViewById(R.id.idEdtNoteLink);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("note");

        // Read from the database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Log.e("acai", dataSnapshot.toString());
                String value = dataSnapshot.getValue(String.class);
                Log.d("TAG", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);

                String noteName = NoteNameEdt.getText().toString();
                String noteDesc = NoteDescEdt.getText().toString();
                String noteImg = NoteImgEdt.getText().toString();
                String noteLink = NoteLinkEdt.getText().toString();
                NoteID = noteName;

                NoteRVModal noteRVModal = new NoteRVModal(noteName, noteDesc, noteImg, noteLink, NoteID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.child("note").child(NoteID).setValue(noteRVModal);
                        Toast.makeText(AddNoteActivity.this, "Note Has Been Added!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddNoteActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddNoteActivity.this, "Fail to add new Note..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}