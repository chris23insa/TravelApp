package com.example.chris.travelorga_kth;

import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.chris.travelorga_kth.helper.DummyDataGenerator;
import com.example.chris.travelorga_kth.recycler_view_main.TripRecyclerViewDataAdapterButton;

public class SearchTripActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trip);

        // Create the recyclerview.
        RecyclerView tripRecyclerView = findViewById(R.id.recyclerview);
        TripRecyclerViewDataAdapterButton tripDataAdapter = new TripRecyclerViewDataAdapterButton(new DummyDataGenerator(this).getAllTrips(),
                this);
        tripRecyclerView.setAdapter(tripDataAdapter);
        ViewCompat.setNestedScrollingEnabled(tripRecyclerView, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        tripRecyclerView.setLayoutManager(gridLayoutManager);

        Button button = findViewById(R.id.doneButton);
        button.setOnClickListener(v -> {
                    Intent result = new Intent();
                    setResult(1, result);
                    finish();
                }
        );
    }
}
