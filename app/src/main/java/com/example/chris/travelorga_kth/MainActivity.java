package com.example.chris.travelorga_kth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import com.bumptech.glide.request.RequestOptions;
import com.example.chris.travelorga_kth.CreateActivity.CreateNewTripActivity;
import com.example.chris.travelorga_kth.base_component.Participants;
import com.example.chris.travelorga_kth.base_component.Trip;
import com.example.chris.travelorga_kth.helper.ViewAnimation;
import com.example.chris.travelorga_kth.network.Scalingo;
import com.example.chris.travelorga_kth.recycler_view_main.TripRecyclerViewDataAdapter;
import com.example.chris.travelorga_kth.utils.ItemClickSupport;
import com.google.android.gms.maps.MapView;
//TODO add name trio
public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fabImport = null;
    private FloatingActionButton fabCreate = null;
    private Intent intentCreateNewActivity;
    private Intent intentMapActivity;
    private Intent intentSearch;
    private Intent intentProfile;
    public static Participants currentUser;
    private boolean isRotate = false;
    public static final String placeHolder = "https://countrylakesdental.com/wp-content/uploads/2016/10/orionthemes-placeholder-image.jpg";
    public static final RequestOptions glideOption =
            new RequestOptions()
                    .placeholder(R.drawable.placeholder)
                    .fitCenter();

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

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


       final long startTime = System.currentTimeMillis();
        final long generate = ((System.currentTimeMillis() - startTime));
        Scalingo.getInstance().getUserDao().retrieve(Login.currentUserId, user -> {
            currentUser = user.toUser();
            Log.d("user", user.toString());
            createRecyclerViewMine();
            createRecyclerViewFriends();
        }, null);

        // Recycler view


        //Intent
        intentCreateNewActivity = new Intent(MainActivity.this, CreateNewTripActivity.class);
        intentMapActivity = new Intent(MainActivity.this, MapsActivity.class);
        intentSearch = new Intent(MainActivity.this, SearchActivity.class);
        intentProfile = new Intent(MainActivity.this, ProfileActivity.class);

        // FAB
        final FloatingActionButton fab = findViewById(R.id.fab);

        fabCreate = findViewById(R.id.fabCall);
        fabImport = findViewById(R.id.fabMic);

        ViewAnimation.init(fabImport);
        ViewAnimation.init(fabCreate);
        fab.setOnClickListener(view -> {
            isRotate = ViewAnimation.rotateFab(view, !isRotate);
            if (isRotate) {
                ViewAnimation.showIn(fabImport);
                ViewAnimation.showIn(fabCreate);
            } else {
                ViewAnimation.showOut(fabImport);
                ViewAnimation.showOut(fabCreate);
            }
        });

        fabImport.setOnClickListener(v -> {
            Intent intent = new Intent(this, SearchTripActivity.class);
            startActivity(intent);
        });

        fabCreate.setOnClickListener(v -> startActivity(intentCreateNewActivity));

        // Bottom navigation view
        BottomNavigationView navigation = findViewById(R.id.activity_main_bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.action_trips);
    }
    private void createRecyclerViewMine() {
        // Create the recyclerview.
        RecyclerView tripRecyclerView = findViewById(R.id.card_view_recycler_list);

        // Create car recycler view data adapter with trip item list.
        currentUser.getListTrip(list -> {
            TripRecyclerViewDataAdapter tripDataAdapter = new TripRecyclerViewDataAdapter(list);
            // Set data adapter.
            tripRecyclerView.setAdapter(tripDataAdapter);

            ViewCompat.setNestedScrollingEnabled(tripRecyclerView, false);

            // Create the grid layout manager with 1 columns.
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
            // Set layout manager.
            tripRecyclerView.setLayoutManager(gridLayoutManager);
            this.configureOnClickRecyclerView(tripRecyclerView, tripDataAdapter);
        });

    }

    private void createRecyclerViewFriends() {
        // Create the recyclerview.
        RecyclerView tripRecyclerView = findViewById(R.id.card_view_recycler_list_friend_trip);

        // Create car recycler view data adapter with trip item list.
        currentUser.getFriendsTrip(list -> {
            list.get(0).getListActivity(i -> Log.d("aa",i.toString()));

            TripRecyclerViewDataAdapter tripDataAdapter = new TripRecyclerViewDataAdapter(list);
            // Set data adapter.
            tripRecyclerView.setAdapter(tripDataAdapter);

            this.configureOnClickRecyclerView(tripRecyclerView, tripDataAdapter);
            ViewCompat.setNestedScrollingEnabled(tripRecyclerView, false);

            // Create the grid layout manager with 1 columns.
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
            // Set layout manager.
            tripRecyclerView.setLayoutManager(gridLayoutManager);});

    }

    // Configure item click on RecyclerView
    private void configureOnClickRecyclerView(RecyclerView rView, final TripRecyclerViewDataAdapter tAdapter) {
        ItemClickSupport.addTo(rView, R.layout.activity_main)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Trip trip = tAdapter.getTrip(position);
                    Intent intent = new Intent(MainActivity.this, TripDetails.class);
                    intent.putExtra("trip", trip);
                    startActivity(intent);
                });
    }

    private void mapInitialiaze() {
        // Fixing Later Map loading Delay
        new Thread(() -> {
            try {
                MapView mv = new MapView(getApplicationContext());
                mv.onCreate(null);
                mv.onPause();
                mv.onDestroy();
            } catch (Exception ignored) {

            }
        }).start();
    }
}
