package com.example.chris.travelorga_kth;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bumptech.glide.request.RequestOptions;
import com.example.chris.travelorga_kth.CreateActivity.CreateNewTripActivity;
import com.example.chris.travelorga_kth.base_component.Participants;
import com.example.chris.travelorga_kth.base_component.Trip;
import com.example.chris.travelorga_kth.helper.BottomNavigationViewHelper;
import com.example.chris.travelorga_kth.helper.ViewAnimation;
import com.example.chris.travelorga_kth.recycler_view_main.TripRecyclerViewDataAdapter;
import com.example.chris.travelorga_kth.utils.ItemClickSupport;
import com.google.android.gms.maps.MapView;

import java.util.ArrayList;
import java.util.Collections;

//TODO add name trio
public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fabImport = null;
    private FloatingActionButton fabCreate = null;
    private Intent intentCreateNewActivity;
    public static Participants currentUser;
    private boolean isRotate = false;
    private TripRecyclerViewDataAdapter tripDataAdapter;
    private final ArrayList<Trip> listTrip = new ArrayList<>();
    public static final RequestOptions glideOption =
            new RequestOptions()
                    .placeholder(R.drawable.placeholder)
                    .fitCenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("TravelApp");
        mapInitialiaze();

        Participants.getCurrentUser(user -> {
            currentUser = user;
                 createRecyclerViewMine();
            createRecyclerViewFriends();
        });

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

        fabCreate.setOnClickListener(v ->{
            intentCreateNewActivity = new Intent(this, CreateNewTripActivity.class);
            startActivity(intentCreateNewActivity);
        }
        );

        BottomNavigationViewHelper.setupNav(this,R.id.action_trips);
    }

    private void createRecyclerViewMine() {
        RecyclerView tripRecyclerView = findViewById(R.id.recycler);

        currentUser.getListTrip(list -> {
            listTrip.removeAll(listTrip);
            listTrip.addAll(list);
            tripDataAdapter = new TripRecyclerViewDataAdapter(listTrip);
            tripRecyclerView.setAdapter(tripDataAdapter);

            ViewCompat.setNestedScrollingEnabled(tripRecyclerView, true);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
            tripRecyclerView.setLayoutManager(gridLayoutManager);
            this.configureOnClickRecyclerView(tripRecyclerView, tripDataAdapter);
        }, this);
    }

    private void createRecyclerViewFriends() {
        RecyclerView tripRecyclerView = findViewById(R.id.recyclerFriends);
        currentUser.getFriendsTrip(list -> {

            TripRecyclerViewDataAdapter tripDataAdapterFriend = new TripRecyclerViewDataAdapter(list);
            tripRecyclerView.setAdapter(tripDataAdapterFriend);

            this.configureOnClickRecyclerView(tripRecyclerView, tripDataAdapterFriend);
            ViewCompat.setNestedScrollingEnabled(tripRecyclerView, true);

            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
            tripRecyclerView.setLayoutManager(gridLayoutManager);
        }, this);

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

    @Override
    public void onResume() {
        super.onResume();
        Log.d("On RESUME ....", "ON RESUME .... ");
        if (tripDataAdapter != null) {
            currentUser.getListTrip(list -> {
                listTrip.removeAll(listTrip);
                listTrip.addAll(list);
                tripDataAdapter.notifyDataSetChanged();
            }, this);

        }
    }
}
