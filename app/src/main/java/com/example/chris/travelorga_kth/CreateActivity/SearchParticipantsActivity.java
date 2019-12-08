package com.example.chris.travelorga_kth.CreateActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.chris.travelorga_kth.MainActivity;
import com.example.chris.travelorga_kth.R;
import com.example.chris.travelorga_kth.base_component.Participants;
import com.example.chris.travelorga_kth.network.Scalingo;
import com.example.chris.travelorga_kth.network.UserModel;
import com.example.chris.travelorga_kth.recycler_view_participants.ParticipantsRecyclerViewAdaptater;
import com.example.chris.travelorga_kth.recycler_view_participants.ParticipantsRecyclerViewAdaptaterAdded;

import java.util.ArrayList;

public class SearchParticipantsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_participants);

        ArrayList<Participants> currentParticipants = (ArrayList<Participants>) getIntent().getExtras().get("list");
        Scalingo.getInstance().getUserDao().retrieveAll(tmpAll -> {
            ArrayList<Participants> allFarticipants = new ArrayList<>();
            for (UserModel um : tmpAll) {
                allFarticipants.add(um.toUser());
            }
            ArrayList<Participants> notSelected = new ArrayList<>(allFarticipants);
            notSelected.removeAll(currentParticipants);

            RecyclerView participantRecyclerView = findViewById(R.id.recyclerview);
            ParticipantsRecyclerViewAdaptater tripDataAdapter =
                    new ParticipantsRecyclerViewAdaptater(notSelected);
            tripDataAdapter.addList(allFarticipants,currentParticipants);
            participantRecyclerView.setAdapter(tripDataAdapter);
            ViewCompat.setNestedScrollingEnabled(participantRecyclerView, false);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
            participantRecyclerView.setLayoutManager(gridLayoutManager);

            RecyclerView participantRecyclerViewAdded = findViewById(R.id.recyclerviewAdded);
            ParticipantsRecyclerViewAdaptaterAdded tripDataAdapterAdded =
                    new ParticipantsRecyclerViewAdaptaterAdded(currentParticipants);
            tripDataAdapterAdded.setOther(allFarticipants,notSelected);
            tripDataAdapterAdded.setOtherRecycler(tripDataAdapter);
            participantRecyclerViewAdded.setAdapter(tripDataAdapterAdded);
            ViewCompat.setNestedScrollingEnabled(participantRecyclerViewAdded, false);
            GridLayoutManager gridLayoutManagerAdded = new GridLayoutManager(this, 1);
            participantRecyclerViewAdded.setLayoutManager(gridLayoutManagerAdded);

            tripDataAdapter.addRecycler(tripDataAdapterAdded);

        });

        findViewById(R.id.doneButton).setOnClickListener(v -> {
            Intent result = new Intent();
            result.putExtra("list",currentParticipants);
            setResult(1,result);
            finish();
        });
    }
}
