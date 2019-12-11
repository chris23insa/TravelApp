package com.example.chris.travelorga_kth.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;

import com.example.chris.travelorga_kth.MainActivity;
import com.example.chris.travelorga_kth.R;
import com.example.chris.travelorga_kth.base_component.Participants;
import com.example.chris.travelorga_kth.base_component.Trip;
import com.example.chris.travelorga_kth.helper.BottomNavigationViewHelper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashSet;
import java.util.Set;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        BottomNavigationViewHelper.setupNav(this,R.id.action_map);
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
        mMap.setOnInfoWindowClickListener(marker -> {
            Intent intentDetailMap = new Intent(MapsActivity.this, MapDetailActivity.class);
            intentDetailMap.putExtra("trip", ((Trip) marker.getTag()));
            startActivity(intentDetailMap);
        });
         MainActivity.currentUser.getListTrip( myTrip -> {
             if(myTrip.size()>0)
                 googleMap.moveCamera(CameraUpdateFactory.newLatLng(myTrip.get(0).getCoord().getLatLng()));
            Set<Trip> friendSTrip = new HashSet<>();
             MainActivity.currentUser.getFriends(list -> {
                 for (Participants p : list){
                     MainActivity.currentUser.getListTrip(friendSTrip::addAll,this);
                 }
             });
             for (Trip trip : myTrip) {
                Marker newMarker = mMap.addMarker((new MarkerOptions().position(trip.getCoord().getLatLng())
                        .title("Trip to  " + trip.getTripName())));
                newMarker.setSnippet(trip.getTripDescription());
                newMarker.setTag(trip);
            }

            for (Trip trip : friendSTrip) {
                Marker newMarker = mMap.addMarker((new MarkerOptions().position(trip.getCoord().getLatLng())
                        .title("Trip to  " + trip.getTripName())).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
                newMarker.setSnippet(trip.getTripDescription());
                newMarker.setTag(trip);
            }
        },this);
    }
}