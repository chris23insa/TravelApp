package com.example.chris.travelorga_kth;

import android.content.Intent;
import android.service.voice.VoiceInteractionService;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.chris.travelorga_kth.base_component.Trip;
import com.example.chris.travelorga_kth.base_component.TripActivity;
import com.example.chris.travelorga_kth.helper.DummyDataGenerator;
import com.example.chris.travelorga_kth.recycler_view_search.MultiViewDataAdapter;

import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    Button mFilterItineraryButton;
    Button mFilterLocationButton;
    Button mFilterActivitiesButton;

    private BottomNavigationView mNavigation;
    private SearchView mSearchView;
    private ArrayList<Trip> mPreviousSearchTripList = null;
    private ArrayList<TripActivity> mPreviousSearchActivityList = null;

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
                    Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.action_search:
                    return true;
                case R.id.action_profile:
                    intent = new Intent(SearchActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.action_map:
                    Intent intentMap = new Intent(SearchActivity.this, MapsActivity.class);
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
        setContentView(R.layout.activity_search);
        setTitle("Search");

        //Bottom navigation view
        mNavigation = (BottomNavigationView) findViewById(R.id.activity_search_bottom_navigation);
        BottomNavigationViewHelper.removeShiftMode(mNavigation);
        //Ugly hack to update the selected navbutton
        mNavigation.setSelectedItemId(R.id.action_search);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // SearchBar

        mSearchView = (SearchView) findViewById(R.id.search_view);
        mSearchView.onActionViewExpanded(); //new Added line
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setQueryHint("Enter search...");
        if(!mSearchView.isFocused()) {
            mSearchView.clearFocus();
        }
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        // History searches
        // TODO : differentiate between activities, itineraries and locations
        initializeItemList();
        // Create the recyclerview.
        RecyclerView searchRecyclerView = (RecyclerView)findViewById(R.id.previous_searches_recyclerview);
        // Create the grid layout manager with 1 columns.
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        // Set layout manager.
        searchRecyclerView.setLayoutManager(gridLayoutManager);
        //ViewCompat.setNestedScrollingEnabled(searchRecyclerView, false);

        // Create recycler view data adapter with trip item list.
        //final TripRecyclerViewDataAdapter tripDataAdapter = new TripRecyclerViewDataAdapter(mPreviousSearchList);
        final MultiViewDataAdapter dataAdapter = new MultiViewDataAdapter(mPreviousSearchTripList, mPreviousSearchActivityList);
        // Set data adapter.
        //searchRecyclerView.setAdapter(tripDataAdapter);
        searchRecyclerView.setAdapter(dataAdapter);


        // Set the listener for the card in the history of searches
        com.example.chris.travelorga_kth.utils.ItemClickSupport.addTo(searchRecyclerView, R.layout.activity_search)
                .setOnItemClickListener(new com.example.chris.travelorga_kth.utils.ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Log.e("TAG", "Position : " + position);
                        //Trip trip = tripDataAdapter.getTrip(position);
                        Trip trip = dataAdapter.getTrip(position);
                        // TODO : Put an intent to redirect toward the activity or the trip depending of it is
                        // a trip or an activity
                    }
                });

        // Button listener
        mFilterItineraryButton = (Button) findViewById(R.id.filter_itineraries);
        mFilterLocationButton = (Button) findViewById(R.id.filter_locations);
        mFilterActivitiesButton = (Button) findViewById(R.id.filter_activities);

        mFilterItineraryButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Click event trigger here
                if (v.isSelected()) {
                    v.setSelected(false);
                } else {
                    v.setSelected(true);
                }
            }
        });

        mFilterLocationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Click event trigger here
                if (v.isActivated()) {
                    v.setActivated(false);
                } else {
                    v.setActivated(true);
                }
            }
        });

        mFilterActivitiesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Click event trigger here
                if (v.isActivated()) {
                    v.setActivated(false);
                } else {
                    v.setActivated(true);
                }
            }
        });

    }

    /* Initialise trip items in list. */
    private void initializeItemList()
    {
        if(mPreviousSearchTripList == null){
            mPreviousSearchTripList = new ArrayList<Trip>();
            mPreviousSearchTripList.addAll( new DummyDataGenerator(this).getMyTrip());

            mPreviousSearchActivityList = new ArrayList<TripActivity>();
            mPreviousSearchActivityList.addAll(mPreviousSearchTripList.get(0).getListActivity());
        }
    }
}
