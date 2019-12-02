package com.example.chris.travelorga_kth;

import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.chris.travelorga_kth.helper.DummyDataGenerator;
import com.example.chris.travelorga_kth.recycler_view_list_activities.ActivityRecycleViewDataAdapter;
import com.example.chris.travelorga_kth.recycler_view_main.TripRecyclerViewDataAdapter;

public class SearchTripActivityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trip_activity);
        // Create the recyclerview.
        RecyclerView activityRecyclerView = findViewById(R.id.recyclerview);
        ActivityRecycleViewDataAdapter tripDataAdapter = new ActivityRecycleViewDataAdapter(new DummyDataGenerator(this).getActivities());
        activityRecyclerView.setAdapter(tripDataAdapter);
        ViewCompat.setNestedScrollingEnabled(activityRecyclerView, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        activityRecyclerView.setLayoutManager(gridLayoutManager);
    }
}
