package com.example.chris.travelorga_kth;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.chris.travelorga_kth.base_component.Participants;
import com.example.chris.travelorga_kth.recycler_view_main.TripRecyclerViewDataAdapter;
import com.example.chris.travelorga_kth.recycler_view_participants.ParticipantsRecyclerViewAdaptater;

import java.util.ArrayList;

public class SearchParticipantsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_participants);

        Participants user = MainActivity.currentUser;
        ArrayList<Participants> participants = (ArrayList<Participants>)getIntent().getExtras().get("list");

        LinearLayout linear = new LinearLayout(this);
        RecyclerView participantRecyclerView = findViewById(R.id.recyclerview);
        ParticipantsRecyclerViewAdaptater tripDataAdapter = new ParticipantsRecyclerViewAdaptater(user.getFriends(),participants);
        participantRecyclerView.setAdapter(tripDataAdapter);
        ViewCompat.setNestedScrollingEnabled(participantRecyclerView, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        participantRecyclerView.setLayoutManager(gridLayoutManager);


        ((Button)findViewById(R.id.doneButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("bb",participants.toString());
                Intent result = new Intent();
                result.putExtra("list",participants);
                Log.d("DEBUG_END_TMP",participants.toString());
                setResult(1,result);
                finish();
            }
        });

    }
}
