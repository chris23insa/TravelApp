package com.example.chris.travelorga_kth.Map;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.chris.travelorga_kth.R;
import com.example.chris.travelorga_kth.base_component.Trip;
import com.example.chris.travelorga_kth.base_component.TripActivity;
import com.example.chris.travelorga_kth.helper.BottomNavigationViewHelper;
import com.example.chris.travelorga_kth.recycler_view_list_activities.ActivityRecycleViewDataAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapDetailActivity extends FragmentActivity implements OnMapReadyCallback {

    private Trip trip;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_detail);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        trip = (Trip)this.getIntent().getExtras().getSerializable("trip");
        RecyclerView activityRecyclerView = findViewById(R.id.activityView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        activityRecyclerView.setLayoutManager(gridLayoutManager);
        ViewCompat.setNestedScrollingEnabled(activityRecyclerView, false);
        trip.getListActivity( list -> {
            ActivityRecycleViewDataAdapter tripDataAdapter = new ActivityRecycleViewDataAdapter(list);
            activityRecyclerView.setAdapter(tripDataAdapter);
        },this);

        BottomNavigationViewHelper.setupNavFinish(this,R.id.action_map);
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
        trip = (Trip)this.getIntent().getExtras().getSerializable("trip");

        googleMap.moveCamera(CameraUpdateFactory.zoomTo(10.0f));
        trip.getListActivity( list -> {
            if(list.size() > 0 )
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(list.get(0).getCoord().getLatLng()));
            else
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(trip.getCoord().getLatLng()));
        },this);

        ((TextView)findViewById(R.id.title)).setText("Trip to "+trip.getTripName());
        trip.getListActivity( list ->{
            for(TripActivity activity : list){
                Marker newMarker = googleMap.addMarker((new MarkerOptions().position(activity.getCoord().getLatLng()).title(activity.place)));
                newMarker.setSnippet(activity.description);
            }

        },this);
    }
}