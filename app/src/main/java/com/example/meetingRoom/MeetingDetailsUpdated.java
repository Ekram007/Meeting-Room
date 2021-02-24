package com.example.meetingRoom;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MeetingDetailsUpdated extends AppCompatActivity {

    private TextView subject, agenda, date, time, place;

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    private Toolbar mToolbar;


    String meetingName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_details_updated);

        Bundle extra = getIntent().getExtras();
        meetingName = extra.getString("meetingName");

        subject = findViewById(R.id.subject);
        agenda = findViewById(R.id.agenda);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        place = findViewById(R.id.place);



        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.child("Meetings").child(meetingName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 if(dataSnapshot.exists()){
                     subject.setText(dataSnapshot.child("Subject").getValue().toString());
                     agenda.setText(dataSnapshot.child("Agenda").getValue().toString());
                     date.setText(dataSnapshot.child("Date").getValue().toString());
                     time.setText(dataSnapshot.child("Time").getValue().toString());
                     place.setText(dataSnapshot.child("Place").getValue().toString());
                 }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mToolbar = (Toolbar) findViewById(R.id.updated_meeting_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(meetingName);

    }
}
