package com.example.meetingRoom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MeetingDetails extends AppCompatActivity {
    private EditText subject, agenda, date, time, place;
    private Button meeting;

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;

    int f= 0;

    String meetingName="";
    String TAG = "hek";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_details);

        Bundle extra = getIntent().getExtras();
        meetingName = extra.getString("meetingName");

        Log.d(TAG, "The "+meetingName);

        if(TextUtils.isEmpty(meetingName))
        {

            int f = 1;
        }


        meeting = findViewById(R.id.update_meeting_button);
        subject = findViewById(R.id.subject);
        agenda = findViewById(R.id.agenda);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        place = findViewById(R.id.place);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        RootRef = FirebaseDatabase.getInstance().getReference();

        meeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RootRef.child("Meetings").child(meetingName).child("Subject").setValue(subject.getText().toString());
                RootRef.child("Meetings").child(meetingName).child("Agenda").setValue(agenda.getText().toString());
                RootRef.child("Meetings").child(meetingName).child("Date").setValue(date.getText().toString());
                RootRef.child("Meetings").child(meetingName).child("Time").setValue(time.getText().toString());
                RootRef.child("Meetings").child(meetingName).child("Place").setValue(place.getText().toString());


                SendUserToMainActivity();

                //RootRef.child("Users").child(meetingName).child("Subject").setValue(subject);
            }
        });



    }
    private void SendUserToMainActivity(){
        Intent mainIntent = new Intent( MeetingDetails.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}
