package com.example.chris.travelorga_kth;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.chris.travelorga_kth.Utils.ItemClickSupport;
import com.example.chris.travelorga_kth.base_component.Trip;
import com.example.chris.travelorga_kth.base_component.TripActivity;
import com.example.chris.travelorga_kth.recycler_view_list_activities.ActivityRecycleViewDataAdapter;

import java.util.ArrayList;

public class TripDetails extends AppCompatActivity {

    private ArrayList<TripActivity> activityItemList = null;

    private BottomNavigationView maNavigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        /**
         * Do something when the item is selected
         *
         * @param item
         * @return
         */
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_trips:
                    Intent intent = new Intent(TripDetails.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.action_search:
                    return true;
                case R.id.action_profile:
                    Intent intentProfile = new Intent(TripDetails.this, ProfileActivity.class);
                    startActivity(intentProfile);
                    return true;
                case R.id.action_map:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_details);
        setTitle("TripDetails");

        Log.d("TripDetails", "Create activity TripDetails");

        Trip trip = (Trip)getIntent().getExtras().get("trip");
        activityItemList = trip.getListActivity();

        // Recycler view

        this.createRecyclerView();

        // Participants listener

        // Bottom navigation view
        maNavigation = (BottomNavigationView) findViewById(R.id.trip_details_bottom_navigation);
        BottomNavigationViewHelper.removeShiftMode(maNavigation);
        maNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        maNavigation.setSelectedItemId(R.id.action_trips);
    }

    @Override
    protected void onResume() {
        super.onResume();

        maNavigation.setOnNavigationItemSelectedListener(null);
        maNavigation.setSelectedItemId(R.id.action_trips);
        maNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /**
     * Create the recycler view
     */
    private void createRecyclerView()
    {
        Log.d("Trip details" , "create recycler view");
        // Create the recyclerview.
        RecyclerView activityRecyclerView = (RecyclerView) findViewById(R.id.card_view_recycler_list_activity);
        // Create the grid layout manager with 1 columns.
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        // Set layout manager.
        activityRecyclerView.setLayoutManager(gridLayoutManager);
        ViewCompat.setNestedScrollingEnabled(activityRecyclerView, false);

        // Create activity recycler view data adapter with activity item list.
        ActivityRecycleViewDataAdapter activityDataAdapter = new ActivityRecycleViewDataAdapter(activityItemList);
        // Set data adapter.
        activityRecyclerView.setAdapter(activityDataAdapter);

        this.configureOnClickRecyclerView(activityRecyclerView, activityDataAdapter);
    }

    // 1 - Configure item click on RecyclerView
    private void configureOnClickRecyclerView(RecyclerView rView, final ActivityRecycleViewDataAdapter tAdapter){
        Log.d("Trip details" , "configure on click");
        ItemClickSupport.addTo(rView, R.layout.trip_details)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Log.e("TAG", "Position : "+position);
                        // 1 - Get trip from adapter
                        TripActivity activity = tAdapter.getActivity(position);
                        // 2 - Show result in a snackbar
                        Intent intent = new Intent(TripDetails.this, ActivityDetails.class);
                        startActivity(intent);
                    }
                });
    }
}
