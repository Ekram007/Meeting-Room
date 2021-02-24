package com.example.meetingRoom;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeetingsFragment extends Fragment
{
    private View meetingsFragmentView;
    private ListView list_view;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> list_of_groups = new ArrayList<>();

    private DatabaseReference GroupRef;



    public MeetingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        meetingsFragmentView = inflater.inflate(R.layout.fragment_meetings, container, false);


        GroupRef = FirebaseDatabase.getInstance().getReference().child("Meetings");


        IntializeFields();


        RetrieveAndDisplayGroups();

        //StaticData();



        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
            {
                String currentMeetingName = adapterView.getItemAtPosition(position).toString();

                Intent groupChatIntent = new Intent(getContext(), MeetingDetailsUpdated.class);
                groupChatIntent.putExtra("meetingName" , currentMeetingName);
                startActivity(groupChatIntent);
            }
        });

        return meetingsFragmentView;
    }




    private void IntializeFields()
    {
        list_view = (ListView) meetingsFragmentView.findViewById(R.id.list_view);
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, list_of_groups);
        list_view.setAdapter(arrayAdapter);
    }




    private void RetrieveAndDisplayGroups()
    {
        GroupRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                Set<String> set = new HashSet<>();
                Iterator iterator = dataSnapshot.getChildren().iterator();

                while (iterator.hasNext())
                {
                    set.add(((DataSnapshot)iterator.next()).getKey());
                }

                list_of_groups.clear();
                list_of_groups.addAll(set);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

//    private void StaticData(){
//        Set<String> set = new HashSet<>();
////        /*set.add("Society Meeting");
////        set.add("The Beatles");
////        set.add("Mess Meeting");
////        //set.add("Guitar Class");
////        set.add("Weekly Meeting");
////        set.add("Tour Meeting");
////        set.add("Budhbarer Meeting");
////        set.add("Project Meeting");
////        //set.add("Shushil");
////        set.add("Golden Team");
////        set.add("Tournament Meeting");
////        //set.add("JoyGuru");
////        set.add("Thursday Night Party");
////        set.add("Chill Party");
////        set.add("Emergency Meeting");
////        //set.add("");*/
//
//
//
//
//        list_of_groups.clear();
//        list_of_groups.addAll(set);
//        arrayAdapter.notifyDataSetChanged();
//    }
}