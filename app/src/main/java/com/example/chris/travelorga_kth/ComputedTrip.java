package com.example.chris.travelorga_kth;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.chris.travelorga_kth.base_component.Participants;
import com.example.chris.travelorga_kth.base_component.TripActivity;
import com.example.chris.travelorga_kth.recycler_view_list_activities.ActivityRecycleViewDataAdapterComputedTrip;
import com.example.chris.travelorga_kth.utils.ItemClickSupport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ComputedTrip extends AppCompatActivity {

    private ArrayList<TripActivity> activityComputedTripList = null;
    private ActivityRecycleViewDataAdapterComputedTrip mDataAdapter;
    private Participants currentUser;

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.action_trips:
                Intent intent = new Intent(ComputedTrip.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_search:
                intent = new Intent(ComputedTrip.this, SearchActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_profile:
                intent = new Intent(ComputedTrip.this, ProfileActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_map:
                Intent intentMap = new Intent(ComputedTrip.this, MapsActivity.class);
                startActivity(intentMap);
                finish();
                return true;
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computed_trip);
        setTitle("Computed trip");
        currentUser = MainActivity.currentUser;

        //Bottom navigation view
        BottomNavigationView mNavigation = findViewById(R.id.bottom_navigation);

        //Ugly hack to update the selected navbutton
        mNavigation.setSelectedItemId(R.id.action_trips);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        TextView titleTrip = findViewById(R.id.trip_name_computed_trip);
        titleTrip.setText("Trip to Madrid !");

        // History searches
        initializeItemList();
        // Create the recyclerview.
        RecyclerView activityComputedTripRecyclerView = findViewById(R.id.recyclerview_computed_trip);
        // Create the grid layout manager with 1 columns.
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        // Set layout manager.
        activityComputedTripRecyclerView.setLayoutManager(gridLayoutManager);
        //ViewCompat.setNestedScrollingEnabled(searchRecyclerView, false);

        // Create recycler view data adapter with trip item list.7
        Log.d("LIST",activityComputedTripList.toString());
        mDataAdapter = new ActivityRecycleViewDataAdapterComputedTrip(activityComputedTripList);
        // Set data adapter.
        //searchRecyclerView.setAdapter(tripDataAdapter);
        activityComputedTripRecyclerView.setAdapter(mDataAdapter);

        // Set the listener for the card in the history of searches
        ItemClickSupport.addTo(activityComputedTripRecyclerView, R.layout.activity_computed_trip)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    TripActivity activity = mDataAdapter.getActivity(position);
                    Intent intent = new Intent(ComputedTrip.this, ActivityDetails.class);
                    intent.putExtra("id", activity.getId());
                    startActivity(intent);
                });
    }

    /* Initialise trip items in list. */
    private void initializeItemList()
    {
        activityComputedTripList = new ArrayList<>();
        // fake data for the moment
        //Trip trip = currentUser.getTrip(1);
        String dateFromStr = "12/07/2020 16:00:00";
        String dateToStr = "12/07/2020 20:00:00";
        String dateFrom2Str = "12/07/2020 08:00:00";
        String dateTo2Str = "12/07/2020 12:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        Date dateFrom = null;
        Date dateTo = null;
        Date dateFrom2 = null;
        Date dateTo2 = null;
        try {
            dateFrom = sdf.parse(dateFromStr);
            dateTo = sdf.parse(dateToStr);
            dateTo2 = sdf.parse(dateTo2Str);
            dateFrom2 = sdf.parse(dateFrom2Str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        activityComputedTripList.add(new TripActivity(24, "Corrida", "https://torero_corrida.com", "https://travelapp-backend.osc-fr1.scalingo.io/activities/madridPlazaMayor.jpg"
                , dateFrom, dateTo , "See the toro and the torero struggle together.", new ArrayList<String>(),
            "Friday : 8pm_Saturday: 7pm", "Free under 18_15€ for senior_10€ for students", 0, 0, this));

        activityComputedTripList.add(new TripActivity(23, "City tour of madrid", "https://madrid.city-tour.com", "https://travelapp-backend.osc-fr1.scalingo.io/activities/madridCityTour.jpg"
                , dateFrom2, dateTo2 , "See the city with an open bus and enjoy the fresh air at the same time.", new ArrayList<String>(),
                "Mon-Fri : 10am-8pm_Weekend : 10am-5pm", "5€ for children_10€ for adults", 0, 0, this));
    }
}
