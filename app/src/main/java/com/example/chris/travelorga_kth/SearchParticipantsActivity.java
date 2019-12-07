package com.example.chris.travelorga_kth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.chris.travelorga_kth.base_component.Participants;
import com.example.chris.travelorga_kth.recycler_view_participants.ParticipantsRecyclerViewAdaptater;
import com.example.chris.travelorga_kth.recycler_view_participants.ParticipantsRecyclerViewAdaptaterAdded;

import java.util.ArrayList;

public class SearchParticipantsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_participants);

        Participants user = MainActivity.currentUser;
        ArrayList<Participants> participants = (ArrayList<Participants>)getIntent().getExtras().get("list");
        user.getFriends(allFarticipants ->{
            ArrayList<Participants> notSelected = new ArrayList<>(allFarticipants);
            notSelected.removeAll(participants);

            RecyclerView participantRecyclerViewAdded = findViewById(R.id.recyclerviewAdded);
            ParticipantsRecyclerViewAdaptaterAdded tripDataAdapterAdded = new ParticipantsRecyclerViewAdaptaterAdded(allFarticipants,participants,notSelected);
            participantRecyclerViewAdded.setAdapter(tripDataAdapterAdded);
            ViewCompat.setNestedScrollingEnabled(participantRecyclerViewAdded, false);
            GridLayoutManager gridLayoutManagerAdded = new GridLayoutManager(this, 1);
            participantRecyclerViewAdded.setLayoutManager(gridLayoutManagerAdded);

            RecyclerView participantRecyclerView = findViewById(R.id.recyclerview);
            ParticipantsRecyclerViewAdaptater tripDataAdapter = new ParticipantsRecyclerViewAdaptater(notSelected,allFarticipants,participants,tripDataAdapterAdded);
            participantRecyclerView.setAdapter(tripDataAdapter);
            ViewCompat.setNestedScrollingEnabled(participantRecyclerView, false);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
            participantRecyclerView.setLayoutManager(gridLayoutManager);
        });






        findViewById(R.id.doneButton).setOnClickListener(v -> {
            Intent result = new Intent();
            result.putExtra("list",participants);
            setResult(1,result);
            finish();
        });

    }
}
