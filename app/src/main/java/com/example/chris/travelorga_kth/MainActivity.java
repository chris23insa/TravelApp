package com.example.chris.travelorga_kth;

import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.TextView;

import com.example.chris.travelorga_kth.utils.ItemClickSupport;
import com.example.chris.travelorga_kth.base_component.Participants;
import com.example.chris.travelorga_kth.base_component.Trip;
import com.example.chris.travelorga_kth.helper.DummyDataGenerator;
import com.example.chris.travelorga_kth.helper.ViewAnimation;
import com.example.chris.travelorga_kth.recycler_view_main.TripRecyclerViewDataAdapter;
import com.google.android.gms.maps.MapView;

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

    private DummyDataGenerator dummyData;

    public static Participants currentUser;


    /**
     * Variable used to know if the fab button is extended or not.
     */
    private boolean isRotate = false;

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
       mapInitialiaze();



        new AsyncTask<Void, Integer, Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                final long startTime = System.currentTimeMillis();
                dummyData = new DummyDataGenerator(MainActivity.this);
                final long generate = ((System.currentTimeMillis() - startTime));
                currentUser = dummyData.Macron;

                // Recycler view

                initializeTripItemList();
                initializeTripItemListFriend();
                final long initialiaze = ((System.currentTimeMillis() - startTime));
                runOnUiThread(() -> {
                    createRecyclerViewMine();
                    createRecyclerViewFriends();
                    long create = ( (System.currentTimeMillis() - startTime));
                    TextView nt = findViewById(R.id.title_my_trip);
                    nt.setText("generate : " + generate + "  initialiaz" + initialiaze + "  create " +create + " \n" );


                });



            return null;
            }

        }.execute();




        //Intent
        intentCreateNewActivity = new Intent(MainActivity.this, CreateNewTripActivity.class);
        intentMapActivity = new Intent(MainActivity.this, MapsActivity.class);
        intentMapActivity.putExtra("myTrips",tripItemList);
        intentMapActivity.putExtra("friendsTrips",tripItemListFriend);
        intentSearch = new Intent(MainActivity.this, SearchActivity.class);
        intentProfile = new Intent(MainActivity.this, ProfileActivity.class);


        // FAB

        final FloatingActionButton fab = findViewById(R.id.fab);

        fabCreate= findViewById(R.id.fabCall);
        fabImport = findViewById(R.id.fabMic);

        ViewAnimation.init(fabImport);
        ViewAnimation.init(fabCreate);
        fab.setOnClickListener(view -> {
            isRotate = ViewAnimation.rotateFab(view, !isRotate);
            if(isRotate){
                ViewAnimation.showIn(fabImport);
                ViewAnimation.showIn(fabCreate);
            }else{
                ViewAnimation.showOut(fabImport);
                ViewAnimation.showOut(fabCreate);
            }
        });

        fabImport.setOnClickListener(v -> {
            Intent intent = new Intent(this,SearchTripActivity.class);
            startActivity(intent);

                });

        fabCreate.setOnClickListener(v -> startActivity(intentCreateNewActivity));


        // Bottom navigation view
        BottomNavigationView navigation = findViewById(R.id.activity_main_bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.action_trips);

    }

    /* Initialise trip items in list. */
    private void initializeTripItemList()
    {
            tripItemList =  dummyData.getMyTrip();
    }

    /* Initialise trip items friends in list. */
    private void initializeTripItemListFriend()
    {
       tripItemListFriend =  dummyData.getFriendsTrip();
    }

    /**
     * Create the recycler view
     */

    private void createRecyclerViewMine()
    {
        // Create the recyclerview.
        RecyclerView tripRecyclerView = findViewById(R.id.card_view_recycler_list);

        // Create car recycler view data adapter with trip item list.
        TripRecyclerViewDataAdapter tripDataAdapter = new TripRecyclerViewDataAdapter(tripItemList);
        // Set data adapter.
        tripRecyclerView.setAdapter(tripDataAdapter);


        ViewCompat.setNestedScrollingEnabled(tripRecyclerView, false);

        // Create the grid layout manager with 1 columns.
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        // Set layout manager.
        tripRecyclerView.setLayoutManager(gridLayoutManager);
        this.configureOnClickRecyclerView(tripRecyclerView, tripDataAdapter);
    }

    private void createRecyclerViewFriends()
    {
        // Create the recyclerview.
        RecyclerView tripRecyclerView = findViewById(R.id.card_view_recycler_list_friend_trip);

        // Create car recycler view data adapter with trip item list.
        TripRecyclerViewDataAdapter tripDataAdapter = new TripRecyclerViewDataAdapter(tripItemListFriend);
        // Set data adapter.
        tripRecyclerView.setAdapter(tripDataAdapter);

        this.configureOnClickRecyclerView(tripRecyclerView, tripDataAdapter);
        ViewCompat.setNestedScrollingEnabled(tripRecyclerView, false);

        // Create the grid layout manager with 1 columns.
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        // Set layout manager.
        tripRecyclerView.setLayoutManager(gridLayoutManager);
    }

    // Configure item click on RecyclerView
    private void configureOnClickRecyclerView(RecyclerView rView, final TripRecyclerViewDataAdapter tAdapter){
        ItemClickSupport.addTo(rView, R.layout.activity_main)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Log.e("TAG", "Position : " + position+ "  " + tAdapter.getTrip(position));
                    Trip trip = tAdapter.getTrip(position);
                    Intent intent = new Intent(MainActivity.this, TripDetails.class);
                    intent.putExtra("trip",trip);
                    startActivity(intent);
                });
    }

    private void mapInitialiaze(){
        // Fixing Later Map loading Delay
        new Thread(() -> {
            try {
                MapView mv = new MapView(getApplicationContext());
                mv.onCreate(null);
                mv.onPause();
                mv.onDestroy();
            }catch (Exception ignored){

            }
        }).start();
    }
}
