package com.example.chris.travelorga_kth;

import android.content.Intent;


import android.content.SharedPreferences;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.chris.travelorga_kth.base_component.Preference;
import com.example.chris.travelorga_kth.base_component.Trip;
import com.example.chris.travelorga_kth.base_component.TripActivity;
import com.example.chris.travelorga_kth.network.ActivityModel;
import com.example.chris.travelorga_kth.network.Scalingo;
import com.example.chris.travelorga_kth.network.ScalingoError;
import com.example.chris.travelorga_kth.network.ScalingoResponse;
import com.example.chris.travelorga_kth.network.TripModel;
import com.example.chris.travelorga_kth.network.UserModel;
import com.example.chris.travelorga_kth.recycler_view_search.MultiViewDataAdapter;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    Button mFilterItineraryButton;
    boolean mFilterItinierary = true;
    Button mFilterLocationButton;
    boolean mFilterLocation = true;
    Button mFilterActivitiesButton;
    boolean mFilterActivities;

    private BottomNavigationView mNavigation;
    private SearchView mSearchView;
    private ArrayList<Trip> mPreviousSearchTripList = null;
    private ArrayList<TripActivity> mPreviousSearchActivityList = null;

    private MultiViewDataAdapter mDataAdapter;

    LinkedList<String> mPrevSearches;
    SharedPreferences mSharedPref;

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
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
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setTitle("Search");

        //Bottom navigation view
        mNavigation = findViewById(R.id.activity_search_bottom_navigation);

        //Ugly hack to update the selected navbutton
        mNavigation.setSelectedItemId(R.id.action_search);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // SearchBar

        mSearchView = findViewById(R.id.search_view);
        mSearchView.onActionViewExpanded(); //new Added line
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setQueryHint("Enter search...");
        if(!mSearchView.isFocused()) {
            mSearchView.clearFocus();
        }
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != "" && query != null) {
                    //queue/dequeue and save search history
                    mPrevSearches.removeFirst();
                    mPrevSearches.add(query);
                    mSharedPref.edit().putString("s1", mPrevSearches.get(0)).apply();
                    mSharedPref.edit().putString("s2", mPrevSearches.get(1)).apply();
                    mSharedPref.edit().putString("s3", mPrevSearches.get(2)).apply();
                    //Sta
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        // History searches
        initializeItemList();
        // Create the recyclerview.
        RecyclerView searchRecyclerView = findViewById(R.id.recyclerview_prev_searches);
        // Create the grid layout manager with 1 columns.
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        // Set layout manager.
        searchRecyclerView.setLayoutManager(gridLayoutManager);
        //ViewCompat.setNestedScrollingEnabled(searchRecyclerView, false);

        // Create recycler view data adapter with trip item list.
        mDataAdapter = new MultiViewDataAdapter(mPreviousSearchTripList, mPreviousSearchActivityList);
        // Set data adapter.
        //searchRecyclerView.setAdapter(tripDataAdapter);
        searchRecyclerView.setAdapter(mDataAdapter);


        // Set the listener for the card in the history of searches
        com.example.chris.travelorga_kth.utils.ItemClickSupport.addTo(searchRecyclerView, R.layout.activity_search)

                .setOnItemClickListener((recyclerView, position, v) -> {
                    Trip trip = mDataAdapter.getTrip(position);
                    // TODO : Put an intent to redirect toward the activity or the trip depending of it is
                    // a trip or an activity
                });

        // Button listener

        mFilterItineraryButton = (Button) findViewById(R.id.filter_Intenerary);
        mFilterLocationButton = (Button) findViewById(R.id.filterlocation);
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


        //get storage
        mSharedPref = getSharedPreferences("prevSearchStringsSearchActivity", MODE_PRIVATE);
        String s1 = mSharedPref.getString("s1", "a+psf9jasoifoasfhiapospoaf");
        String s2 = mSharedPref.getString("s2", "a+psf9jasoifoasfhiapospoaf");
        String s3 = mSharedPref.getString("s3", "a+psf9jasoifoasfhiapospoaf");

        mPrevSearches = new LinkedList<>();
        mPrevSearches.add(s1);
        mPrevSearches.add(s2);
        mPrevSearches.add(s3);

        //mPrevSearches.add("londres");
        //mPrevSearches.add("paris");
        //mPrevSearches.add("siberia");

        fetchData();


    }

    /* Initialise trip items in list. */
    private void initializeItemList()
    {
        mPreviousSearchTripList = new ArrayList<>();
        mPreviousSearchActivityList = new ArrayList<>();

        //mPreviousSearchActivityList.addAll(mPreviousSearchTripList.get(0).getListActivity());
    }

    private void fetchData() {
        Scalingo.getInstance().getTripDao().retrieveAll(
                new ScalingoResponse.SuccessListener<List<TripModel>>() {
                    @Override
                    public void onResponse(List<TripModel> allTrips) {
                        Gson gson= new Gson();
                        try {
                            for (TripModel tripModel : allTrips) {
                                JSONObject tripJson = tripModel.jsonify();

                                Trip t = new Trip(tripModel.getName(), 0, tripModel.getDateFrom().toString(),
                                        tripModel.getDateTo().toString(), tripModel.getDescription(), null, null,
                                        (int) tripModel.getBudget(), Preference.MUSEUM, SearchActivity.this);

                                // Some filter logic based on the buttons
                                if (mFilterItinierary) {
                                    for (String prevS : mPrevSearches) {
                                        if (t.getTripName().toLowerCase().contains(prevS.toLowerCase())) {
                                            mDataAdapter.addTrip(t);
                                        }
                                    }
                                }

                            }
                        } catch (Exception e ) {
                            Log.e("jsonify trips", e.toString());
                        }

                        int hej = 1;
                    }
                },
                new ScalingoResponse.ErrorListener() {
                    @Override
                    public void onError(ScalingoError error) {
                        Log.e("on retrieve all trips", error.toString());
                    }
                }
        );
        Scalingo.getInstance().getActivityDao().retrieveAll(
                new ScalingoResponse.SuccessListener<List<ActivityModel>>() {
                    @Override
                    public void onResponse(List<ActivityModel> response) {
                        int hej = 1;
                    }
                },
                new ScalingoResponse.ErrorListener() {
                    @Override
                    public void onError(ScalingoError error) {
                        int hej = 1;
                        Log.e("on retrieve all activities", error.toString());
                    }
                }
        );
    }


}
