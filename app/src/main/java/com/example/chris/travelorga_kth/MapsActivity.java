package com.example.chris.travelorga_kth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.chris.travelorga_kth.base_component.Trip;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private BottomNavigationView mNavigation;
  
    private ArrayList<Trip> myTrip;
    private ArrayList<Trip>  friendSTrip;

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
                    Intent intent = new Intent(MapsActivity.this, MainActivity.class);
                    startActivity(intent);
                    return true;
                }
                case R.id.action_search:
                    return true;
                case R.id.action_profile:
                    Intent intent = new Intent(MapsActivity.this, ProfileActivity.class);
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
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Bottom navigation view
        mNavigation = (BottomNavigationView) findViewById(R.id.activity_main_bottom_navigation);
        BottomNavigationViewHelper.removeShiftMode(mNavigation);
        //Ugly hack to update the selected navbutton
        mNavigation.setSelectedItemId(R.id.action_map);
        //mNavigation.getMenu().getItem(R.id.action_profile).set
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
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
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intentCreateNewActivity = new Intent(MapsActivity.this, MapDetailActivity.class);
                intentCreateNewActivity.putExtra("trip", ((Trip) marker.getTag()));
                startActivity(intentCreateNewActivity);
            }
        });
        myTrip = (ArrayList<Trip>)getIntent().getExtras().get("myTrips");
        friendSTrip = (ArrayList<Trip>)getIntent().getExtras().get("friendsTrips");

        for (Trip trip : myTrip) {
                    Marker newMarker = mMap.addMarker((new MarkerOptions().position(trip.getCoord().getLatLng()).title("Trip to  " + trip.getTripName())));
                    newMarker.setSnippet(trip.getTripDescription());
                    newMarker.setTag(trip);
        }
      
        for (Trip trip : friendSTrip) {
                    Marker newMarker = mMap.addMarker((new MarkerOptions().position(trip.getCoord().getLatLng()).title("Trip to  " + trip.getTripName())).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                    newMarker.setSnippet(trip.getTripDescription());
                    newMarker.setTag(trip);
        }
    }
}