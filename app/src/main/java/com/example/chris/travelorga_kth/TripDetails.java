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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chris.travelorga_kth.recycler_view_list_activities.ActivityRecycleViewDataAdapter;
import com.example.chris.travelorga_kth.utils.ItemClickSupport;
import com.example.chris.travelorga_kth.base_component.Trip;
import com.example.chris.travelorga_kth.base_component.TripActivity;

import java.util.ArrayList;

public class TripDetails extends AppCompatActivity {

    private ArrayList<TripActivity> activityItemList = null;

    private BottomNavigationView maNavigation;

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
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
                    Intent intentSearch = new Intent(TripDetails.this, SearchActivity.class);
                    startActivity(intentSearch);
                    finish();
                    return true;
                case R.id.action_profile:
                    Intent intentProfile = new Intent(TripDetails.this, ProfileActivity.class);
                    startActivity(intentProfile);
                    finish();
                    return true;
                case R.id.action_map:
                    Intent intentMap = new Intent(TripDetails.this, MapsActivity.class);
                    startActivity(intentMap);
                    finish();
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
        ((ImageView)findViewById(R.id.toolbarImage)).setImageResource(trip.getTripImageId());
        activityItemList = trip.getListActivity();
        Log.d("aa",activityItemList.toString());



        ((TextView)findViewById(R.id.content_details_trip)).setText(trip.getTripDescription());
        ((TextView)findViewById(R.id.textFrom)).setText(trip.getTripDateFrom());
        ((TextView)findViewById(R.id.textTo)).setText(trip.getTripDateTo());
        ((TextView)findViewById(R.id.textBudget)).setText(trip.getBudget());
        ((TextView)findViewById(R.id.textPreference)).setText(trip.getPreference().toString());
        ((TextView)findViewById(R.id.content_details_trip)).setText(trip.getTripDescription());


        // Recycler view

        createRecyclerView();

        // Participants listener

        // Bottom navigation view
        maNavigation = findViewById(R.id.trip_details_bottom_navigation);
        maNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

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
        RecyclerView activityRecyclerView = findViewById(R.id.card_view_recycler_list_activity);
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
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Log.e("TAG", "Position : "+position);
                    // 1 - Get trip from adapter
                    TripActivity activity = tAdapter.getActivity(position);
                    // 2 - Show result in a snackbar
                    Intent intent = new Intent(TripDetails.this, ActivityDetails.class);
                    intent.putExtra("activity",activity);
                    startActivity(intent);
                });
    }
}