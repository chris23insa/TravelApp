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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.chris.travelorga_kth.network.Scalingo;
import com.example.chris.travelorga_kth.network.ScalingoError;
import com.example.chris.travelorga_kth.network.ScalingoResponse;
import com.example.chris.travelorga_kth.network.UserModel;
import com.example.chris.travelorga_kth.utils.ItemClickSupport;
import com.example.chris.travelorga_kth.base_component.Participants;
import com.example.chris.travelorga_kth.base_component.Trip;
import com.example.chris.travelorga_kth.helper.DummyDataGenerator;
import com.example.chris.travelorga_kth.helper.ViewAnimation;
import com.example.chris.travelorga_kth.recycler_view_main.TripRecyclerViewDataAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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
                    finish();
                    return true;
                }
                case R.id.action_profile: {
                    startActivity(intentProfile);
                    finish();
                    return true;
                }
                case R.id.action_map: {
                    startActivity(intentMapActivity);
                    finish();
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
        currentUser = dummyData.Macron;

        // Recycler view

        initializeTripItemList();
        initializeTripItemListFriend();

        createRecyclerViewMine();

        createRecyclerViewFriends();

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

        //////////////// NETWORK INIT //////////////////////////////////////////////////////////////
        Scalingo.init(this);

//        Scalingo.getInstance().authenticate("moustic@mail.com", "qwerty",
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.w("Failed authentication", error.getMessage());
//                    }
//                });

        Scalingo.getInstance().authenticate("moustic@mail.com", "qwerty",
                new ScalingoResponse.SuccessListener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // -----------------------------------------------------------------------------
                        Scalingo.getInstance().getUserDao().retrieve(
                                35L,
                                new ScalingoResponse.SuccessListener<UserModel>() {
                                    @Override
                                    public void onResponse(UserModel user) {
                                        Log.w("Userdao retrieve", user.toString());
                                    }
                                },
                                new ScalingoResponse.ErrorListener() {
                                    @Override
                                    public void onError(ScalingoError error) {
                                        Log.w("ERROR", error);
                                    }
                                });
                        // -----------------------------------------------------------------------------
                    }
                }, new ScalingoResponse.ErrorListener() {
                    @Override
                    public void onError(ScalingoError error) {

                    }
                });


        // The abstraction
//        server.authenticate(this, username, password,
//                (response) -> {doYourStuff()},
//                (error) -> {ohNoAnError()});

        //Make a volley queue
//        final RequestQueue requestQueue = Volley.newRequestQueue(this);
//        //base URL
//        String URL = "https://travelapp-backend.osc-fr1.scalingo.io";
//        //
//        JSONObject authJsonBody = new JSONObject();
//        try{
//            authJsonBody.put("username", "moustic@mail.com");
//            authJsonBody.put("password", "qwerty");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        //Create a volley post request, using the url/authentication and json containing username/pw
//        final JsonObjectRequest authRequest = new JsonObjectRequest(
//                Request.Method.POST,
//                (URL+"/authentication"),
//                authJsonBody,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.e("Response: ", response.toString());
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.e("Response Error: ", error.toString());
//                    }
//                }
//        );
//        //enqueue the request
//        requestQueue.add(authRequest);
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
                        intent.putExtra("activity",trip);
                        startActivity(intent);
                    }
                });
    }
}
