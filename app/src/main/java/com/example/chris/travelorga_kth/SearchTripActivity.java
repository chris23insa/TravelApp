package com.example.chris.travelorga_kth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.chris.travelorga_kth.base_component.Trip;
import com.example.chris.travelorga_kth.network.Scalingo;
import com.example.chris.travelorga_kth.network.TripModel;
import com.example.chris.travelorga_kth.recycler_view_main.TripRecyclerViewDataAdapterButton;

import java.util.List;
import java.util.stream.Collectors;

public class SearchTripActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trip);

        // Create the recyclerview.
        RecyclerView tripRecyclerView = findViewById(R.id.recyclerview);
        //TODO get allTrip
        Scalingo.getInstance().getTripDao().retrieveFriendsTrips(Login.currentUserId, list -> {
            List<Trip> listTrip = list.stream().map(TripModel::toTrip).collect(Collectors.toList());
            TripRecyclerViewDataAdapterButton tripDataAdapter = new TripRecyclerViewDataAdapterButton(listTrip,this);
            tripRecyclerView.setAdapter(tripDataAdapter);
            ViewCompat.setNestedScrollingEnabled(tripRecyclerView, false);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
            tripRecyclerView.setLayoutManager(gridLayoutManager);
        });


        Button button = findViewById(R.id.doneButton);
        button.setOnClickListener(v -> {
                    Intent result = new Intent();
                    setResult(1, result);
                    finish();
                }
        );
    }
}
