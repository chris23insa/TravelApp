package com.example.chris.travelorga_kth;

import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.chris.travelorga_kth.helper.DummyDataGenerator;
import com.example.chris.travelorga_kth.recycler_view_main.TripRecyclerViewDataAdapter;

public class SearchTripActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trip);

        // Create the recyclerview.
        RecyclerView tripRecyclerView = findViewById(R.id.recyclerview);
        TripRecyclerViewDataAdapter tripDataAdapter = new TripRecyclerViewDataAdapter(new DummyDataGenerator(this).getAllTrips());
        tripRecyclerView.setAdapter(tripDataAdapter);
        ViewCompat.setNestedScrollingEnabled(tripRecyclerView, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        tripRecyclerView.setLayoutManager(gridLayoutManager);
    }
}
