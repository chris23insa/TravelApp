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
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private ArrayList<Trip> tripItemList = null;

    private ArrayList<Trip> tripItemListFriend = null;

    private FloatingActionButton fabImport = null;

    private FloatingActionButton fabCreate = null;

    private Intent intentCreateNewActivity;
    private Intent intentMapActivity;
    private Intent intentMainActivity;

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
                case R.id.action_trips:
                    startActivity(intentMainActivity);
                    return true;
                case R.id.action_search:
                    return true;
                case R.id.action_profile:
                    return true;
                case R.id.action_map:
                    startActivity(intentMapActivity);
                    return true;
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

        createRecyclerViewMine();

        createRecyclerViewFriends();

        //Intent
        intentMainActivity = new Intent(MainActivity.this, MainActivity.class);
        intentCreateNewActivity = new Intent(MainActivity.this, CreateNewTripActivity.class);
        intentMapActivity = new Intent(MainActivity.this, MapsActivity.class);
        intentMapActivity.putExtra("myTrips",tripItemList);
        intentMapActivity.putExtra("friendsTrips",tripItemListFriend);


        // FAB

        FloatingActionButton fab = findViewById(R.id.fab);

        fabCreate= findViewById(R.id.fabMic);
        fabImport = findViewById(R.id.fabCall);

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
                Snackbar.make(view, "You click on the FAB", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
                Snackbar.make(v, "You click on the FAB creation", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(intentCreateNewActivity);


            }
        });


        // Bottom navigation view
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.activity_main_bottom_navigation);
        BottomNavigationViewHelper.removeShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

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
    }
}
