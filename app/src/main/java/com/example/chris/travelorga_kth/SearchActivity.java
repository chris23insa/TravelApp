package com.example.chris.travelorga_kth;

import android.content.Intent;


import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import android.util.Log;
import android.view.View;
import android.widget.Button;

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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    Button mFilterItineraryButton;
    Button mFilterLocationButton;
    Button mFilterActivitiesButton;

    private BottomNavigationView mNavigation;
    private SearchView mSearchView;
    private ArrayList<Trip> mPreviousSearchTripList = null;
    private ArrayList<TripActivity> mPreviousSearchActivityList = null;

    private MultiViewDataAdapter mDataAdapter;

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

//        Scalingo.getInstance().getTripDao().retrieveOrganizedTrips(
//                35L,
//                new ScalingoResponse.SuccessListener<List<TripModel>>() {
//                    @Override
//                    public void onResponse(List<TripModel> response) {
//                        Log.w("Retrieve organized trips", response.toString());
//                    }
//                },
//                new ScalingoResponse.ErrorListener() {
//                    @Override
//                    public void onError(ScalingoError error) {
//
//                    }
//                }
//        );
//        Scalingo.getInstance().getTripDao().retrieveFriendsTrips(
//                35L,
//                new ScalingoResponse.SuccessListener<List<TripModel>>() {
//                    @Override
//                    public void onResponse(List<TripModel> response) {
//                        Log.w("Retrieve organized trips", response.toString());
//                    }
//                },
//                new ScalingoResponse.ErrorListener() {
//                    @Override
//                    public void onError(ScalingoError error) {
//
//                    }
//                }
//        );
//
//        Scalingo.getInstance().getUserDao().retrieve(
//                35L,
//                new ScalingoResponse.SuccessListener<UserModel>() {
//                    @Override
//                    public void onResponse(UserModel user) {
//                        Log.w("Retrieve user #35 : ", user.toString());
//                    }
//                },
//                new ScalingoResponse.ErrorListener() {
//                    @Override
//                    public void onError(ScalingoError error) {
//                        Log.w("ERROR", error);
//                    }
//                });
//
//        Scalingo.getInstance().getUserDao().retrieveFriends(
//                35L,
//                new ScalingoResponse.SuccessListener<List<UserModel>>() {
//                    @Override
//                    public void onResponse(List<UserModel> response) {
//                        Log.w("Retrieve friends", response.toString());
//                    }
//                },
//                new ScalingoResponse.ErrorListener() {
//                    @Override
//                    public void onError(ScalingoError error) {
//                        Log.w("ERROR", error);
//                    }
//                });
//


        //final List<TripModel> TMList = new ArrayList<>();
        Scalingo.getInstance().getUserDao().retrieveAll(
                new ScalingoResponse.SuccessListener<List<UserModel>>() {
                    @Override
                    public void onResponse(List<UserModel> users) {
                        for (int i = 0; i < users.size(); ++i) {
                            // -----------------------------------------------------------------------------
                            // Add activities associated to the user
                            Scalingo.getInstance().getActivityDao().retrieveUserActivities(
                                    (users.get(i).getId()),
                                    new ScalingoResponse.SuccessListener<List<ActivityModel>>() {
                                        @Override
                                        public void onResponse(List<ActivityModel> userActivities) {
                                            Gson gson= new Gson();
                                            try {
                                                for (ActivityModel userActivity : userActivities) {
                                                    JSONObject jsonObject = userActivity.jsonify();
                                                    TripActivity tA = gson.fromJson(jsonObject.toString(), TripActivity.class);
                                                    mDataAdapter.addTripActivity(tA);
                                                    //searchRecyclerView.add
                                                    //Filter search list?
                                                }
                                            } catch (Exception e ) {
                                                Log.e("jsonify activities", e.toString());
                                            }
                                        }
                                    },
                                    null
                            );
                            // Add trips associated to the user
                            Scalingo.getInstance().getTripDao().retrieveOrganizedTrips(
                                    (users.get(i).getId()),
                                    new ScalingoResponse.SuccessListener<List<TripModel>>() {
                                        @Override
                                        public void onResponse(List<TripModel> userTrips) {
                                            Gson gson= new Gson();
                                            try {
                                                for (TripModel userTrip : userTrips) {
                                                    JSONObject jsonObject = userTrip.jsonify();
                                                    Trip t = gson.fromJson(jsonObject.toString(), Trip.class);
                                                    //////mDataAdapter.addTrip(t);
                                                    //searchRecyclerView.add
                                                    //Filter search list?
                                                }
                                            } catch (Exception e ) {
                                                Log.e("jsonify trips", e.toString());
                                            }
                                        }
                                    },
                                    null
                            );
                        }
                        Log.w("Retrieve All", users.toString());
                    }
                },
                null
        );

//        Scalingo.getInstance().getActivityDao().retrieveFriendsActivities(
//                35L,
//                new ScalingoResponse.SuccessListener<List<ActivityModel>>() {
//                    @Override
//                    public void onResponse(List<ActivityModel> response) {
//
//                    }
//                },
//                new ScalingoResponse.ErrorListener() {
//                    @Override
//                    public void onError(ScalingoError error) {
//
//                    }
//                }
//        );
//
//        Scalingo.getInstance().getActivityDao().retrieveUserActivities(
//                35L,
//                new ScalingoResponse.SuccessListener<List<ActivityModel>>() {
//                    @Override
//                    public void onResponse(List<ActivityModel> response) {
//                        Log.w("Retrieve friends", response.toString());
//                    }
//                },
//                new ScalingoResponse.ErrorListener() {
//                    @Override
//                    public void onError(ScalingoError error) {
//
//                    }
//                }
//        );
    }

    /* Initialise trip items in list. */
    private void initializeItemList()
    {
        mPreviousSearchTripList = new ArrayList<>();
        mPreviousSearchActivityList = new ArrayList<>();

        //mPreviousSearchActivityList.addAll(mPreviousSearchTripList.get(0).getListActivity());
    }
}
