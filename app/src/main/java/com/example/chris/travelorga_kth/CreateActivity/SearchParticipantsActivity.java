package com.example.chris.travelorga_kth.CreateActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.ToggleButton;

import com.example.chris.travelorga_kth.MainActivity;
import com.example.chris.travelorga_kth.R;
import com.example.chris.travelorga_kth.base_component.Participants;
import com.example.chris.travelorga_kth.helper.BottomNavigationViewHelper;
import com.example.chris.travelorga_kth.recycler_view_participants.ParticipantsRecyclerViewAdaptater;
import com.example.chris.travelorga_kth.recycler_view_participants.ParticipantsRecyclerViewAdaptaterAdded;

import java.util.ArrayList;
import java.util.List;

public class SearchParticipantsActivity extends AppCompatActivity {

    private ParticipantsRecyclerViewAdaptater  tripDataAdapter;
    private ArrayList<Participants> currentParticipants;
    private final ArrayList AllParticipant = new ArrayList();
    private final ArrayList notSelected = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_participants);

        currentParticipants = (ArrayList<Participants>) getIntent().getExtras().get("list");
        ToggleButton friendsButton = findViewById(R.id.filter_friend);

        friendsButton.setChecked(false);
        friendsButton.setBackgroundColor(Color.GRAY);

        if (friendsButton.isChecked())
            MainActivity.currentUser.getFriends(tmpAll ->{
                refresh(tmpAll);
                createAdapter(notSelected);
                process(filter(AllParticipant), currentParticipants,tripDataAdapter,notSelected);
            });
        else {
           Participants.getAllUser(tmpAll -> {
                refresh(tmpAll);
                createAdapter(notSelected);
                process(filter(AllParticipant), currentParticipants,tripDataAdapter,notSelected);
            });
        }

        friendsButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
                buttonView.setBackgroundColor(Color.GREEN);
            else
                buttonView.setBackgroundColor(Color.GRAY);
            if (isChecked)
                MainActivity.currentUser.getFriends(tmpAll ->{
                    refresh(tmpAll);
                    tripDataAdapter.notifyDataSetChanged();
                });
            else {
                Participants.getAllUser(tmpAll -> {
                    refresh(tmpAll);
                    tripDataAdapter.notifyDataSetChanged();
                });
            }
        });

        findViewById(R.id.doneButton).setOnClickListener(v -> {
            Intent result = new Intent();
            result.putExtra("list", currentParticipants);
            setResult(1, result);
            finish();
        });

        BottomNavigationViewHelper.setupNav(this,R.id.action_search); }

    private void refresh(List<Participants> tmpAll){
        AllParticipant.removeAll(AllParticipant);
        AllParticipant.addAll(tmpAll);
        notSelected.removeAll(notSelected);
        notSelected.addAll(AllParticipant);
        notSelected.removeAll(currentParticipants);

    }

    private void createAdapter(ArrayList<Participants> notSelected){
        tripDataAdapter   = new ParticipantsRecyclerViewAdaptater(notSelected);
    }

    private void process(ArrayList<Participants> allFarticipants,
                         ArrayList<Participants> currentParticipants,
                         ParticipantsRecyclerViewAdaptater tripDataAdapter,
                         ArrayList<Participants> notSelected) {
        {

            RecyclerView participantRecyclerView = findViewById(R.id.recyclerview);
            tripDataAdapter.addList(currentParticipants);
            participantRecyclerView.setAdapter(tripDataAdapter);
            ViewCompat.setNestedScrollingEnabled(participantRecyclerView, false);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
            participantRecyclerView.setLayoutManager(gridLayoutManager);

            RecyclerView participantRecyclerViewAdded = findViewById(R.id.recyclerviewAdded);
            ParticipantsRecyclerViewAdaptaterAdded tripDataAdapterAdded =
                    new ParticipantsRecyclerViewAdaptaterAdded(currentParticipants);
            tripDataAdapterAdded.setOther(notSelected);
            tripDataAdapterAdded.setOtherRecycler(tripDataAdapter);
            participantRecyclerViewAdded.setAdapter(tripDataAdapterAdded);
            ViewCompat.setNestedScrollingEnabled(participantRecyclerViewAdded, false);
            GridLayoutManager gridLayoutManagerAdded = new GridLayoutManager(this, 1);
            participantRecyclerViewAdded.setLayoutManager(gridLayoutManagerAdded);

            tripDataAdapter.addRecycler(tripDataAdapterAdded);

        }
    }

    private ArrayList<Participants> filter(ArrayList<Participants> l) {
        SearchView view = findViewById(R.id.search_view);
        String query = view.getQuery().toString().toLowerCase();
        if(query.equals(""))
            return l;
        ArrayList<Participants> tmp = new ArrayList<>();
        for (Participants p : l) {
            if (!query.equals("") && !(query == null)) {
                if (p.getUsername().contains(query))
                    tmp.add(p);
            }
        }
        return tmp;
    }
}
