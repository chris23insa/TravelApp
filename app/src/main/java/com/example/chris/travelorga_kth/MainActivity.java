package com.example.chris.travelorga_kth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.chris.travelorga_kth.Utils.ItemClickSupport;
import com.example.chris.travelorga_kth.Utils.ViewAnimation;
import com.example.chris.travelorga_kth.recycler_view_main.TripRecyclerViewDataAdapter;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    private ArrayList<Trip> tripItemList = null;

    private ArrayList<Trip> tripItemListFriend = null;

    private FloatingActionButton fabImport = null;

    private FloatingActionButton fabCreate = null;

    private Intent intentCreateNewActivity;
    private Intent intentMapActivity;
    private Intent intentSearch;
    private Intent intentProfile;

    DummyDataGenerator dummyData;

    /**
     * Variable used to know if the fab button is extended or not.
     */
    boolean isRotate = false;

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
                case R.id.action_trips: {
                    return true;
                }
                case R.id.action_search: {
                    startActivity(intentSearch);
                    return true;
                }
                case R.id.action_profile: {
                    startActivity(intentProfile);
                    return true;
                }
                case R.id.action_map: {
                    startActivity(intentMapActivity);
                    return true;
                }
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("TravelApp");
        dummyData = new DummyDataGenerator(this);

        // Recycler view

        initializeTripItemList();
        initializeTripItemListFriend();

        //Intent
        intentCreateNewActivity = new Intent(MainActivity.this, CreateNewTripActivity.class);
        intentMapActivity = new Intent(MainActivity.this, MapsActivity.class);
        intentMapActivity.putExtra("myTrips",tripItemList);
        intentMapActivity.putExtra("friendsTrips",tripItemListFriend);
        intentSearch = new Intent(MainActivity.this, SearchActivity.class);
        intentProfile = new Intent(MainActivity.this, ProfileActivity.class);

        createRecyclerViewMine();

        createRecyclerViewFriends();

        // FAB

        FloatingActionButton fab = findViewById(R.id.fab);

        fabCreate= findViewById(R.id.fabCall);
        fabImport = findViewById(R.id.fabMic);

        ViewAnimation.init(fabImport);
        ViewAnimation.init(fabCreate);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRotate = ViewAnimation.rotateFab(view, !isRotate);
                if(isRotate){
                    ViewAnimation.showIn(fabImport);
                    ViewAnimation.showIn(fabCreate);
                }else{
                    ViewAnimation.showOut(fabImport);
                    ViewAnimation.showOut(fabCreate);
                }
            }
        });

        fabImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "You click on the FAB import", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        fabCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intentCreateNewActivity);
            }
        });

        // Bottom navigation view
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.activity_main_bottom_navigation);
        BottomNavigationViewHelper.removeShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.action_trips);

    }

    /* Initialise trip items in list. */
    private void initializeTripItemList()
    {
        if(tripItemList == null)
        {
            tripItemList = new ArrayList<Trip>();
            tripItemList.addAll((dummyData.getMyTrip()));
        }
    }

    /* Initialise trip items friends in list. */
    private void initializeTripItemListFriend()
    {
        if(tripItemListFriend == null)
        {
            tripItemListFriend = new ArrayList<Trip>();
            tripItemListFriend.addAll((dummyData.getFriendsTrip()));
        }
    }

    /**
     * Create the recycler view
     */
    private void createRecyclerViewMine()
    {
        // Create the recyclerview.
        RecyclerView tripRecyclerView = (RecyclerView)findViewById(R.id.card_view_recycler_list);
        // Create the grid layout manager with 1 columns.
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        // Set layout manager.
        tripRecyclerView.setLayoutManager(gridLayoutManager);
        ViewCompat.setNestedScrollingEnabled(tripRecyclerView, false);

        // Create car recycler view data adapter with trip item list.
        TripRecyclerViewDataAdapter tripDataAdapter = new TripRecyclerViewDataAdapter(tripItemList);
        // Set data adapter.
        tripRecyclerView.setAdapter(tripDataAdapter);

        this.configureOnClickRecyclerView(tripRecyclerView, tripDataAdapter);
    }

    private void createRecyclerViewFriends()
    {
        // Create the recyclerview.
        RecyclerView tripRecyclerView = (RecyclerView)findViewById(R.id.card_view_recycler_list_friend_trip);
        // Create the grid layout manager with 1 columns.
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        // Set layout manager.
        tripRecyclerView.setLayoutManager(gridLayoutManager);
        ViewCompat.setNestedScrollingEnabled(tripRecyclerView, false);

        // Create car recycler view data adapter with trip item list.
        TripRecyclerViewDataAdapter tripDataAdapter = new TripRecyclerViewDataAdapter(tripItemListFriend);
        // Set data adapter.
        tripRecyclerView.setAdapter(tripDataAdapter);

        this.configureOnClickRecyclerView(tripRecyclerView, tripDataAdapter);
    }

    // Configure item click on RecyclerView
    private void configureOnClickRecyclerView(RecyclerView rView, final TripRecyclerViewDataAdapter tAdapter){
        ItemClickSupport.addTo(rView, R.layout.activity_main)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Log.e("TAG", "Position : " + position);
                        Trip trip = tAdapter.getTrip(position);
                        Intent intent = new Intent(MainActivity.this, TripDetails.class);
                        startActivity(intent);
                    }
                });
    }
}
