package com.example.chris.travelorga_kth;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chris.travelorga_kth.base_component.Participants;
import com.example.chris.travelorga_kth.base_component.Trip;
import com.example.chris.travelorga_kth.base_component.TripActivity;
import com.example.chris.travelorga_kth.recycler_view_list_activities.ActivityRecycleViewDataAdapter;
import com.example.chris.travelorga_kth.utils.ItemClickSupport;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TripDetails extends Activity {

    private BottomNavigationView maNavigation;
    Trip trip;

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
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
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_details);

        setTitle("TripDetails");

        trip = (Trip)getIntent().getExtras().get("trip");
        ImageView img = findViewById(R.id.toolbarImage);
        Glide.with(img).load(trip.getImageURL()).apply(MainActivity.glideOption).into(img);

        ((TextView)findViewById(R.id.content_details_trip)).setText(trip.getTripDescription());
        ((TextView)findViewById(R.id.textFrom)).setText(trip.getTripDateFrom());
        ((TextView)findViewById(R.id.textTo)).setText(trip.getTripDateTo());
        ((TextView)findViewById(R.id.textBudget)).setText(Integer.toString(trip.getBudget()));
        ((TextView)findViewById(R.id.textPreference)).setText(trip.getPreference().toString());
        ((TextView)findViewById(R.id.content_details_trip)).setText(trip.getTripDescription());

        LinearLayout view = findViewById(R.id.profile_friends_list_layout);
        trip.getListParticipants( list ->{
        for(Participants participants : list) {
            CircleImageView imageProfile = participants.getProfileImage(this);
            if (imageProfile.getParent() != null)
                ((ViewGroup)imageProfile.getParent()).removeView(imageProfile);
            view.addView(imageProfile);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10,40,10,20);
            imageProfile.setLayoutParams(lp);
            imageProfile.getLayoutParams().height = 150;
            imageProfile.getLayoutParams().width = 150;
        }
    });


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
        RecyclerView activityRecyclerView = findViewById(R.id.card_view_recycler_list_activity);
        // Create the grid layout manager with 1 columns.
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        // Set layout manager.
        activityRecyclerView.setLayoutManager(gridLayoutManager);
        ViewCompat.setNestedScrollingEnabled(activityRecyclerView, false);

        // Create activity recycler view data adapter with activity item list.
        trip.getListActivity( activityItemList ->{
            ActivityRecycleViewDataAdapter activityDataAdapter = new ActivityRecycleViewDataAdapter(activityItemList);
            // Set data adapter.
            activityRecyclerView.setAdapter(activityDataAdapter);

            this.configureOnClickRecyclerView(activityRecyclerView, activityDataAdapter);
        });

    }

    // 1 - Configure item click on RecyclerView
    private void configureOnClickRecyclerView(RecyclerView rView, final ActivityRecycleViewDataAdapter tAdapter){
        ItemClickSupport.addTo(rView, R.layout.trip_details)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    TripActivity activity = tAdapter.getActivity(position);
                    // 2 - Show result in a snackbar
                    Intent intent = new Intent(TripDetails.this, ActivityDetails.class);
                    intent.putExtra("id",activity.getId());
                    startActivity(intent);
                });
    }
}
