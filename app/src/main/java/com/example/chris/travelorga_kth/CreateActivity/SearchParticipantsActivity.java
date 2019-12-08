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
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.chris.travelorga_kth.MainActivity;
import com.example.chris.travelorga_kth.MapsActivity;
import com.example.chris.travelorga_kth.ProfileActivity;
import com.example.chris.travelorga_kth.R;
import com.example.chris.travelorga_kth.SearchActivity;
import com.example.chris.travelorga_kth.base_component.Participants;
import com.example.chris.travelorga_kth.network.Scalingo;
import com.example.chris.travelorga_kth.network.UserModel;
import com.example.chris.travelorga_kth.recycler_view_participants.ParticipantsRecyclerViewAdaptater;
import com.example.chris.travelorga_kth.recycler_view_participants.ParticipantsRecyclerViewAdaptaterAdded;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchParticipantsActivity extends AppCompatActivity {

    ParticipantsRecyclerViewAdaptater  tripDataAdapter;
    ArrayList<Participants> currentParticipants;
    ArrayList AllParticipant = new ArrayList();
    ArrayList notSelected = new ArrayList();

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.action_trips:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_search:
                Intent intentSearch = new Intent(this, SearchActivity.class);
                startActivity(intentSearch);
                finish();
                return true;
            case R.id.action_profile:
                Intent intentProfile = new Intent(this, ProfileActivity.class);
                startActivity(intentProfile);
                finish();
                return true;
            case R.id.action_map:
                Intent intentMap = new Intent(this, MapsActivity.class);
                startActivity(intentMap);
                finish();
                return true;
        }
        return false;
    };

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
                createAdapter(AllParticipant,currentParticipants,notSelected);
                process(filter(AllParticipant), currentParticipants,tripDataAdapter,notSelected);
            });
        else {
            Scalingo.getInstance().getUserDao().retrieveAll(tmpAll -> {
                refresh(tmpAll.stream().map(i-> i.toUser()).collect(Collectors.toCollection(ArrayList::new)));
                createAdapter(AllParticipant,currentParticipants,notSelected);
                process(filter(AllParticipant), currentParticipants,tripDataAdapter,notSelected);
            });
        }

        friendsButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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
                    Scalingo.getInstance().getUserDao().retrieveAll(tmpAll -> {
                        refresh(tmpAll.stream().map(i-> i.toUser()).collect(Collectors.toCollection(ArrayList::new)));
                        tripDataAdapter.notifyDataSetChanged();
                    });
                }

            }
        });

        findViewById(R.id.doneButton).setOnClickListener(v -> {
            Intent result = new Intent();
            result.putExtra("list", currentParticipants);
            setResult(1, result);
            finish();
        });

        BottomNavigationView mNavigation = findViewById(R.id.bottom_navigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void refresh(List<Participants> tmpAll){
        AllParticipant.removeAll(AllParticipant);
        AllParticipant.addAll(tmpAll);
        notSelected.removeAll(notSelected);
        notSelected.addAll(AllParticipant);
        notSelected.removeAll(currentParticipants);

    }

    public void createAdapter(ArrayList<Participants>allFarticipants,
                                                           ArrayList<Participants>currentParticipants,
                                                           ArrayList<Participants> notSelected){

        ParticipantsRecyclerViewAdaptater adapter =
                new ParticipantsRecyclerViewAdaptater(notSelected);
        tripDataAdapter   = adapter;
    }

    private void process(ArrayList<Participants> allFarticipants,
                         ArrayList<Participants> currentParticipants,
                         ParticipantsRecyclerViewAdaptater tripDataAdapter,
                         ArrayList<Participants> notSelected) {
        {

            RecyclerView participantRecyclerView = findViewById(R.id.recyclerview);
            tripDataAdapter.addList(allFarticipants, currentParticipants);
            participantRecyclerView.setAdapter(tripDataAdapter);
            ViewCompat.setNestedScrollingEnabled(participantRecyclerView, false);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
            participantRecyclerView.setLayoutManager(gridLayoutManager);

            RecyclerView participantRecyclerViewAdded = findViewById(R.id.recyclerviewAdded);
            ParticipantsRecyclerViewAdaptaterAdded tripDataAdapterAdded =
                    new ParticipantsRecyclerViewAdaptaterAdded(currentParticipants);
            tripDataAdapterAdded.setOther(allFarticipants, notSelected);
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
