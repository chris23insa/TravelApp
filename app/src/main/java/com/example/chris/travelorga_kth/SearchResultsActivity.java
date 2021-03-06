package com.example.chris.travelorga_kth;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.Button;

import com.example.chris.travelorga_kth.base_component.Preference;
import com.example.chris.travelorga_kth.base_component.Trip;
import com.example.chris.travelorga_kth.base_component.TripActivity;
import com.example.chris.travelorga_kth.helper.BottomNavigationViewHelper;
import com.example.chris.travelorga_kth.network.ActivityModel;
import com.example.chris.travelorga_kth.network.Scalingo;
import com.example.chris.travelorga_kth.network.TripModel;
import com.example.chris.travelorga_kth.recycler_view_search.MultiViewDataAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SearchResultsActivity extends AppCompatActivity {

    private Button mFilterItineraryButton;
    private Button mFilterLocationButton;
    private Button mFilterActivitiesButton;
    private boolean noFilter = true;

    private ArrayList<Trip> mSearchResultsTrips = null;
    private ArrayList<TripActivity> mSearchResultsActivities = null;

    private MultiViewDataAdapter mDataAdapter;

    private String mSearchString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        setTitle("Search Results");

        //Reading the search parameters
        Bundle b = getIntent().getExtras();
        mSearchString = b.getString("keyword");

        BottomNavigationViewHelper.setupNav(this,R.id.action_search);
        // SearchBar
        SearchView mSearchResultsView = findViewById(R.id.results_search_view);
        mSearchResultsView.onActionViewExpanded(); //new Added line
        mSearchResultsView.setIconifiedByDefault(false);
        mSearchResultsView.setQueryHint("Enter search...");
        if(!mSearchResultsView.isFocused()) {
            mSearchResultsView.clearFocus();
        }
        mSearchResultsView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.equals("") && query != null) {
                    if (!query.equals(mSearchString)) {
                        mSearchString = query;
                        updateFilterVisual();
                        mSearchResultsActivities.clear();
                        mSearchResultsTrips.clear();
                        mDataAdapter.clearData();
                        mDataAdapter.notifyDataSetChanged();
                        fetchData();
                    }
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
        RecyclerView searchRecyclerView = findViewById(R.id.recycler);
        // Create the grid layout manager with 1 columns.
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        // Set layout manager.
        searchRecyclerView.setLayoutManager(gridLayoutManager);
        //ViewCompat.setNestedScrollingEnabled(searchRecyclerView, false);

        // Create recycler view data adapter with trip item list.
        mDataAdapter = new MultiViewDataAdapter(mSearchResultsTrips, mSearchResultsActivities);
        // Set data adapter.
        //searchRecyclerView.setAdapter(tripDataAdapter);
        searchRecyclerView.setAdapter(mDataAdapter);


        // Set the listener for the card in the history of searches
        com.example.chris.travelorga_kth.utils.ItemClickSupport.addTo(searchRecyclerView, R.layout.activity_search_results)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Trip trip = mDataAdapter.getTrip(position);
                    // TODO : Put an intent to redirect toward the activity or the trip depending of it is
                    // a trip or an activity
                });

        // Button listener

        mFilterItineraryButton = findViewById(R.id.results_filter_Itinerary);
        mFilterItineraryButton.setActivated(false);
        mFilterLocationButton = findViewById(R.id.results_filter_location);
        mFilterLocationButton.setActivated(false);
        mFilterActivitiesButton = findViewById(R.id.results_filter_activities);
        mFilterActivitiesButton.setActivated(false);
        mFilterItineraryButton.setOnClickListener(v -> {
            boolean activated = v.isActivated();
            // Click event trigger here
            if (v.isSelected()) {
                v.setActivated(false);
            } else {
                v.setActivated(true);
            }
            noFilter = !mFilterLocationButton.isActivated() && !mFilterItineraryButton.isActivated() && !mFilterActivitiesButton.isActivated();
            updateFilterVisual();
            mSearchResultsActivities.clear();
            mSearchResultsTrips.clear();
            mDataAdapter.clearData();
            mDataAdapter.notifyDataSetChanged();
            fetchData();
        });

        mFilterLocationButton.setOnClickListener(v -> {
            // Click event trigger here
            if (v.isActivated()) {
                v.setActivated(false);
            } else {
                v.setActivated(true);
            }
            noFilter = !mFilterLocationButton.isActivated() && !mFilterItineraryButton.isActivated() && !mFilterActivitiesButton.isActivated();
            boolean loc = mFilterLocationButton.isActivated();
            boolean iti = mFilterItineraryButton.isActivated();
            boolean act = mFilterActivitiesButton.isActivated();
            updateFilterVisual();
            mSearchResultsActivities.clear();
            mSearchResultsTrips.clear();
            mDataAdapter.clearData();
            mDataAdapter.notifyDataSetChanged();
            fetchData();
        });

        mFilterActivitiesButton.setOnClickListener(v -> {
            // Click event trigger here
            if (v.isActivated()) {
                v.setActivated(false);
            } else {
                v.setActivated(true);
            }
            noFilter = !mFilterLocationButton.isActivated() && !mFilterItineraryButton.isActivated() && !mFilterActivitiesButton.isActivated();
            updateFilterVisual();
            mSearchResultsActivities.clear();
            mSearchResultsTrips.clear();
            mDataAdapter.clearData();
            mDataAdapter.notifyDataSetChanged();
            fetchData();
        });


        mFilterActivitiesButton.setActivated(b.getBoolean("Activity"));
        mFilterLocationButton.setActivated(b.getBoolean("Location"));
        mFilterItineraryButton.setActivated(b.getBoolean("Itinerary"));

        noFilter = !mFilterLocationButton.isActivated() && !mFilterItineraryButton.isActivated() && !mFilterActivitiesButton.isActivated();

        fetchData();

    }

    /* Initialise trip items in list. */
    private void initializeItemList()
    {
        mSearchResultsTrips = new ArrayList<>();
        mSearchResultsActivities = new ArrayList<>();

    }

    private void fetchData() {
        Scalingo.getInstance().getTripDao().retrieveAll(
                allTrips -> {
                    Gson gson= new Gson();
                    try {
                        for (TripModel tripModel : allTrips) {
                            //JSONObject tripJson = tripModel.jsonify();
                            Trip t = new Trip(0,tripModel.getName(),tripModel.getPlace() ,tripModel.getPictureUrl(),
                                    tripModel.getDateFrom(),
                                    tripModel.getDateTo(), tripModel.getDescription(), (int)tripModel.getBudget(),
                                    Preference.BAR,tripModel.getLatitude(),tripModel.getLongitude(),tripModel.getOwnerId(),tripModel.getCreated(),this);

                            // Some filter logic based on the buttons
                            if (mFilterItineraryButton.isActivated() || noFilter) {
                                if (t.getTripName().toLowerCase().contains(mSearchString.toLowerCase())) {
                                    mDataAdapter.addTrip(t);
                                }
                            }

                        }
                    } catch (Exception e ) {
                        Log.e("jsonify trips", e.toString());
                    }
                },
                error -> Log.e("on retrieve all trips", error.toString())
        );
        Scalingo.getInstance().getActivityDao().retrieveAll(
                allActivities -> {
                    Gson gson= new Gson();
                    try {
                        ArrayList<String> tmp= new ArrayList<>();
                        tmp.add("");
                        for (ActivityModel activityModel : allActivities) {
                            //JSONObject activityJson = activityModel.jsonify();
                            ArrayList<String> openingHours = new ArrayList<>();
                            ArrayList<String> price = new ArrayList<>();
                            price.add(activityModel.getPricing());
                            openingHours.add(activityModel.getOpeningTime());
                            TripActivity tA =  new TripActivity(0,
                                    activityModel.getName(), "","",
                                    activityModel.getDateFrom(), activityModel.getDateTo(),
                                    activityModel.getDescription(),tmp,
                                    activityModel.getOpeningTime(),"", activityModel.getLatitude(),activityModel.getLongitude(),this);

                            // Some filter logic based on the buttons
                            if (mFilterActivitiesButton.isActivated() || noFilter) {
                                if (tA.getName().toLowerCase().contains(mSearchString.toLowerCase())) {
                                    mDataAdapter.addTripActivity(tA);
                                }
                            }

                        }
                    } catch (Exception e ) {
                        Log.e("jsonify trips", e.toString());
                    }

                },
                error -> {
                    int hej = 1;
                    Log.e("on retrieve all activities", error.toString());
                }
        );
    }

    private void updateFilterVisual() {
        if (noFilter) {
            mFilterActivitiesButton.setBackgroundResource(R.drawable.button_shape_filter);
            mFilterActivitiesButton.setTextColor(Color.WHITE);
            mFilterLocationButton.setBackgroundResource(R.drawable.button_shape_filter);
            mFilterLocationButton.setTextColor(Color.WHITE);
            mFilterItineraryButton.setBackgroundResource(R.drawable.button_shape_filter);
            mFilterItineraryButton.setTextColor(Color.WHITE);
        } else {
            if (mFilterActivitiesButton.isActivated()) {
                mFilterActivitiesButton.setBackgroundResource(R.drawable.button_shape_filter);
                mFilterActivitiesButton.setTextColor(Color.WHITE);
            } else {
                mFilterActivitiesButton.setBackgroundResource(R.drawable.button_shape_filter_unselected);
                mFilterActivitiesButton.setTextColor(Color.BLACK);
            }
            if (mFilterItineraryButton.isActivated()) {
                mFilterItineraryButton.setBackgroundResource(R.drawable.button_shape_filter);
                mFilterItineraryButton.setTextColor(Color.WHITE);
            } else {
                mFilterItineraryButton.setBackgroundResource(R.drawable.button_shape_filter_unselected);
                mFilterItineraryButton.setTextColor(Color.BLACK);
            }
            if (mFilterLocationButton.isActivated()) {
                mFilterLocationButton.setBackgroundResource(R.drawable.button_shape_filter);
                mFilterLocationButton.setTextColor(Color.WHITE);
            } else {
                mFilterLocationButton.setBackgroundResource(R.drawable.button_shape_filter_unselected);
                mFilterLocationButton.setTextColor(Color.BLACK);
            }
        }
    }

}
