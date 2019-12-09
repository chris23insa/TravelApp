package com.example.chris.travelorga_kth.CreateActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.support.v7.widget.SearchView;

import com.example.chris.travelorga_kth.MainActivity;
import com.example.chris.travelorga_kth.MapsActivity;
import com.example.chris.travelorga_kth.ProfileActivity;
import com.example.chris.travelorga_kth.R;
import com.example.chris.travelorga_kth.SearchActivity;
import com.example.chris.travelorga_kth.base_component.TripActivity;
import com.example.chris.travelorga_kth.network.ActivityModel;
import com.example.chris.travelorga_kth.network.Scalingo;
import com.example.chris.travelorga_kth.recycler_view_list_activities.ActivityRecycleViewDataAdapterAdded;
import com.example.chris.travelorga_kth.recycler_view_list_activities.ActivityRecycleViewDataAdapterButton;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SearchTripActivityActivity extends AppCompatActivity {

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.action_trips:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_search:
                Intent intentSearch = new Intent(this, SearchActivity.class);
                startActivity(intentSearch);
                finish();
                return true;
            case R.id.action_profile:
                Intent intentProfile = new Intent(this, ProfileActivity.class);
                startActivity(intentProfile);
                finish();
                return true;
            case R.id.action_map:
                Intent intentMap = new Intent(this, MapsActivity.class);
                startActivity(intentMap);
                finish();
                return true;
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trip_activity);
        // Create the recyclerview.

        ArrayList<TripActivity> currentActivity = (ArrayList<TripActivity>)getIntent().getExtras().get("list");

        Scalingo.getInstance().getActivityDao().retrieveAll(
                listA ->{
                    Log.w("aa",listA.toString());
            ArrayList<TripActivity> allActivities = listA.stream().map(u -> u.toActivity(this)).collect(Collectors.toCollection(ArrayList::new));

            ArrayList<TripActivity> noSelectedActivity = new ArrayList<>(allActivities);
            noSelectedActivity.removeAll(currentActivity);

            RecyclerView activityRecyclerViewAdded = findViewById(R.id.recyclerviewAdded);
            ActivityRecycleViewDataAdapterAdded addedActivityAdapter = new ActivityRecycleViewDataAdapterAdded(currentActivity);
            addedActivityAdapter.addList(allActivities,noSelectedActivity);
            activityRecyclerViewAdded.setAdapter(addedActivityAdapter);
            ViewCompat.setNestedScrollingEnabled(activityRecyclerViewAdded, false);
            GridLayoutManager gridLayoutManagerAdded = new GridLayoutManager(this, 1);
            activityRecyclerViewAdded.setLayoutManager(gridLayoutManagerAdded);

            RecyclerView activityRecyclerView = findViewById(R.id.recyclerview);
            ActivityRecycleViewDataAdapterButton tripDataAdapter = new ActivityRecycleViewDataAdapterButton(
                    noSelectedActivity);
            tripDataAdapter.addRecyler(addedActivityAdapter);
            tripDataAdapter.addList(allActivities,currentActivity);
            activityRecyclerView.setAdapter(tripDataAdapter);
            ViewCompat.setNestedScrollingEnabled(activityRecyclerView, false);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
            activityRecyclerView.setLayoutManager(gridLayoutManager);

            addedActivityAdapter.setOtherRecycler(tripDataAdapter);

            addedActivityAdapter.setOtherRecycler(tripDataAdapter);
        },null);


        findViewById(R.id.doneButton).setOnClickListener(v -> {
            Intent result = new Intent();
            result.putExtra("list",currentActivity);
            setResult(1,result);
            finish();
        });
        BottomNavigationView mNavigation = findViewById(R.id.bottom_navigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    public ArrayList<TripActivity> filter(ArrayList<TripActivity> l ){
        SearchView view = findViewById(R.id.search_view);
        String query = view.getQuery().toString().toLowerCase();
        ArrayList<TripActivity> tmp = new ArrayList<>();
        for(TripActivity a : l){
            if(a.description.contains(query) || a.place.contains(query) || a.getName().contains(query))
                tmp.add(a);
        }
        return  tmp;
    }
}
