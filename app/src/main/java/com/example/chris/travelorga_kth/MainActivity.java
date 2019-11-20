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
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<TripRecyclerViewItem> tripItemList = null;

    private ArrayList<TripRecyclerViewItem> tripItemListFriend = null;

    private FloatingActionButton fabImport = null;

    private FloatingActionButton fabCreate = null;

    private BottomNavigationView mNavigation;

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
                    return true;
                case R.id.action_search:
                    return true;
                case R.id.action_profile:
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
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
        setContentView(R.layout.activity_main);


        setTitle("TravelApp");

        // Recycler view

        initializeTripItemList();
        initializeTripItemListFriend();

        createRecyclerViewMine();

        createRecyclerViewFriends();

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
            }
        });


        // Bottom navigation view
        mNavigation = (BottomNavigationView) findViewById(R.id.activity_main_bottom_navigation);
        BottomNavigationViewHelper.removeShiftMode(mNavigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    protected void onResume() {
        super.onResume();

        mNavigation.setOnNavigationItemReselectedListener(null);
        mNavigation.setSelectedItemId(R.id.action_trips);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /* Initialise trip items in list. */
    private void initializeTripItemList()
    {
        if(tripItemList == null)
        {
            tripItemList = new ArrayList<TripRecyclerViewItem>();
            tripItemList.add(new TripRecyclerViewItem("Londres", R.drawable.londres, "17/11/2019", "21/11/2019", "Trip in Londres for 3 days with the best !"));
            tripItemList.add(new TripRecyclerViewItem("Paris", R.drawable.tour_eiffel, "16/09/2017", "20/09/2017", "Trip in Paris to see the eiffel tower, unbelievable !"));
            tripItemList.add(new TripRecyclerViewItem("New-York", R.drawable.new_york, "02/03/2019", "10/03/2019", "New-yok, city of light with my partner in crime."));
            tripItemList.add(new TripRecyclerViewItem("Stockholm", R.drawable.stockholm, "30/04/2019", "05/05/2019", "Lake, Park, Cold, description of our journey."));
        }
    }

    /* Initialise trip items friends in list. */
    private void initializeTripItemListFriend()
    {
        if(tripItemListFriend == null)
        {
            tripItemListFriend = new ArrayList<TripRecyclerViewItem>();
            tripItemListFriend.add(new TripRecyclerViewItem("Madrid", R.drawable.madrid, "11/04/2019", "20/04/2019", "Trip in Madrid to discover the tortillas and corrida."));
            tripItemListFriend.add(new TripRecyclerViewItem("Hamburg", R.drawable.hamburg, "17/10/2018", "20/10/2018", "Trip in Hamburg, Amazing ! "));
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
