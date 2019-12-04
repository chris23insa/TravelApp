package com.example.chris.travelorga_kth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.chris.travelorga_kth.base_component.TripActivity;
import com.example.chris.travelorga_kth.helper.DummyDataGenerator;
import com.example.chris.travelorga_kth.recycler_view_list_activities.ActivityRecycleViewDataAdapterButton;

import java.util.ArrayList;

public class SearchTripActivityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trip_activity);
        // Create the recyclerview.
        RecyclerView activityRecyclerView = findViewById(R.id.recyclerview);
        ArrayList<TripActivity> list = (ArrayList<TripActivity>)getIntent().getExtras().get("list");
        ActivityRecycleViewDataAdapterButton tripDataAdapter = new ActivityRecycleViewDataAdapterButton(new DummyDataGenerator(this).getActivities(),list);
        activityRecyclerView.setAdapter(tripDataAdapter);
        ViewCompat.setNestedScrollingEnabled(activityRecyclerView, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        activityRecyclerView.setLayoutManager(gridLayoutManager);

        findViewById(R.id.doneButton).setOnClickListener(v -> {
            Intent result = new Intent();
            result.putExtra("list",list);
            setResult(1,result);
            finish();
        });
    }
}
