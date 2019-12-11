package com.example.chris.travelorga_kth;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.ToggleButton;

import com.example.chris.travelorga_kth.base_component.Trip;
import com.example.chris.travelorga_kth.helper.BottomNavigationViewHelper;
import com.example.chris.travelorga_kth.recycler_view_main.TripRecyclerViewDataAdapterButton;

import java.util.ArrayList;
import java.util.List;

public class SearchTripActivity extends AppCompatActivity {

    private TripRecyclerViewDataAdapterButton tripDataAdapter;
    private final ArrayList<Trip> all = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trip);

        // Create the recyclerview.
        ToggleButton friend = findViewById(R.id.filter_friend);

        friend.setChecked(false);
        friend.setBackgroundColor(Color.GRAY);


        if (friend.isChecked())
            MainActivity.currentUser.getFriendsTrip(tmpAll ->
            {
                refresh(tmpAll);
                createAdapter(all);
                process(tripDataAdapter);
            },this);
        else {
               Trip.getAll(this,tmpAll -> {
                    refresh(tmpAll);
                    createAdapter(all);
                    process(tripDataAdapter);
                });
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
                }, this);
            else {
                Trip.getAll(this,
                        tmpAll -> {
                            refresh(tmpAll);
                            tripDataAdapter.notifyDataSetChanged();
                        });
            }
        });
        BottomNavigationViewHelper.setupNav(this,R.id.action_search); }

    private void process(TripRecyclerViewDataAdapterButton tripDataAdapter
    ) {
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
    }

    private void createAdapter(
            ArrayList<Trip> notSelected) {

        tripDataAdapter = new TripRecyclerViewDataAdapterButton(notSelected);
    }
}
