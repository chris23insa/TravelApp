package com.example.chris.travelorga_kth;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.chris.travelorga_kth.base_component.Trip;
import com.example.chris.travelorga_kth.base_component.TripActivity;
import com.example.chris.travelorga_kth.recycler_view_list_activities.ActivityRecycleViewDataAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapDetailActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Trip trip;
    private BottomNavigationView mNavigation;

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.action_trips: {
                        Intent intent = new Intent(MapDetailActivity.this, MainActivity.class);
                        startActivity(intent);
                        return true;
                    }
                    case R.id.action_search:
                        Intent intentSearch = new Intent(MapDetailActivity.this, SearchActivity.class);
                        startActivity(intentSearch);
                        return true;
                    case R.id.action_profile:
                        Intent intentProfile = new Intent(MapDetailActivity.this, ProfileActivity.class);
                        startActivity(intentProfile);
                        return true;
                    case R.id.action_map:
                        Intent intentMap = new Intent(MapDetailActivity.this, MapsActivity.class);
                        startActivity(intentMap);
                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_detail);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Bottom navigation view
        mNavigation = findViewById(R.id.activity_map_details_bottom_navigation);

        //Ugly hack to update the selected navbutton
        mNavigation.setSelectedItemId(R.id.action_map);
        //mNavigation.getMenu().getItem(R.id.action_profile).set
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        trip = (Trip)this.getIntent().getExtras().getSerializable("trip");
        RecyclerView activityRecyclerView = findViewById(R.id.activityView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        activityRecyclerView.setLayoutManager(gridLayoutManager);
        ViewCompat.setNestedScrollingEnabled(activityRecyclerView, false);
        ActivityRecycleViewDataAdapter tripDataAdapter = new ActivityRecycleViewDataAdapter(trip.getListActivity());
        // Set data adapter.
        activityRecyclerView.setAdapter(tripDataAdapter);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        trip = (Trip)this.getIntent().getExtras().getSerializable("trip");

        mMap.moveCamera(CameraUpdateFactory.zoomTo(10.0f));
        if(trip.getListActivity().size() > 0 )
            mMap.moveCamera(CameraUpdateFactory.newLatLng(trip.getListActivity().get(0).coord.getLatLng()));
        else
            mMap.moveCamera(CameraUpdateFactory.newLatLng(trip.getCoord().getLatLng()));

        ((TextView)findViewById(R.id.title_my_activity)).setText("Trip to "+trip.getTripName());
        for (TripActivity activity : trip.getListActivity()) {
            Marker newMarker = mMap.addMarker((new MarkerOptions().position(activity.coord.getLatLng()).title(activity.place)));
            newMarker.setSnippet(activity.description);
        }

    }
}