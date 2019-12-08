package com.example.chris.travelorga_kth;

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

import com.example.chris.travelorga_kth.base_component.Trip;
import com.example.chris.travelorga_kth.network.Scalingo;
import com.example.chris.travelorga_kth.network.TripModel;
import com.example.chris.travelorga_kth.recycler_view_main.TripRecyclerViewDataAdapterButton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchTripActivity extends AppCompatActivity {

    private TripRecyclerViewDataAdapterButton tripDataAdapter;
    private ArrayList<Trip> current;
    private final ArrayList<Trip> all = new ArrayList();
    private final ArrayList<Trip> notSelected = new ArrayList();

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
        setContentView(R.layout.activity_search_trip);

        // Create the recyclerview.
        ToggleButton friend = findViewById(R.id.filter_friend);

        friend.setChecked(false);
        friend.setBackgroundColor(Color.GRAY);


        if (friend.isChecked())

            Scalingo.getInstance().getTripDao().retrieveFriendsTrips(Login.currentUserId, tmpAll ->
            {
                refresh(tmpAll.stream().map(TripModel::toTrip).collect(Collectors.toCollection(ArrayList::new)));
                createAdapter(all, current, notSelected);
                process(filter(all), current, tripDataAdapter, notSelected);
            });
        else {
            try {
                Scalingo.getInstance().getTripDao().retrieveAll(tmpAll -> {
                    refresh(tmpAll.stream().map(TripModel::toTrip).collect(Collectors.toCollection(ArrayList::new)));
                    createAdapter(all, current, notSelected);
                    process(filter(all), current, tripDataAdapter, notSelected);
                }, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        friend.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                buttonView.setBackgroundColor(Color.GREEN);
            } else
                buttonView.setBackgroundColor(Color.GRAY);
            if (isChecked)
                MainActivity.currentUser.getFriendsTrip(tmpAll -> {
                    refresh(tmpAll);
                    tripDataAdapter.notifyDataSetChanged();
                });
            else {
                Scalingo.getInstance().getTripDao().retrieveAll(
                        tmpAll -> {
                            refresh(tmpAll.stream().map(i -> i.toTrip()).collect(Collectors.toCollection(ArrayList::new)));
                            tripDataAdapter.notifyDataSetChanged();
                        },null);
            }
        });
        BottomNavigationView mNavigation = findViewById(R.id.bottom_navigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void process(ArrayList<Trip> allFarticipants,
                         ArrayList<Trip> currentParticipants,
                         TripRecyclerViewDataAdapterButton tripDataAdapter,
                         ArrayList<Trip> notSelected) {
        RecyclerView tripRecyclerView = findViewById(R.id.recyclerview);
        tripRecyclerView.setAdapter(tripDataAdapter);
        ViewCompat.setNestedScrollingEnabled(tripRecyclerView, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        tripRecyclerView.setLayoutManager(gridLayoutManager);
    }


    private ArrayList<Trip> filter(ArrayList<Trip> l) {
        SearchView view = findViewById(R.id.search_view);
        String query = view.getQuery().toString().toLowerCase();
        ArrayList<Trip> Alist = new ArrayList<>(l);
        if (query.equals(""))
            return Alist;
        ArrayList<Trip> tmp = new ArrayList<>();
        for (Trip a : Alist) {
            if (a.getTripDescription().contains(query) || a.getPlace().contains(query) || a.getPreference().toString().contains(query))
                tmp.add(a);
        }
        return tmp;
    }

    private void refresh(List<Trip> tmpAll) {
        all.removeAll(all);
        all.addAll(tmpAll);
        notSelected.removeAll(notSelected);
        notSelected.addAll(all);
        notSelected.removeAll(current);
    }


    private void createAdapter(ArrayList<Trip> allFarticipants,
                               ArrayList<Trip> currentParticipants,
                               ArrayList<Trip> notSelected) {

        tripDataAdapter = new TripRecyclerViewDataAdapterButton(notSelected);
    }
}
