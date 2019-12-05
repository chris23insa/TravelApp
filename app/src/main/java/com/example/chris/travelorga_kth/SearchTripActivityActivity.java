package com.example.chris.travelorga_kth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.chris.travelorga_kth.base_component.TripActivity;
import com.example.chris.travelorga_kth.network.Scalingo;
import com.example.chris.travelorga_kth.recycler_view_list_activities.ActivityRecycleViewDataAdapterAdded;
import com.example.chris.travelorga_kth.recycler_view_list_activities.ActivityRecycleViewDataAdapterButton;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SearchTripActivityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trip_activity);
        // Create the recyclerview.

        ArrayList<TripActivity> list = (ArrayList<TripActivity>)getIntent().getExtras().get("list");
        //TODO get all activitye
        Scalingo.getInstance().getActivityDao().retrieveUserActivities(MainActivity.currentUserId, listA ->{
            ArrayList<TripActivity> allActivities = new ArrayList<>(listA.stream().map(i -> i.toActivity()).collect(Collectors.toList()));

            ArrayList<TripActivity> noSelectedActivity = new ArrayList<>(allActivities);
            noSelectedActivity.removeAll(list);

            RecyclerView activityRecyclerViewAdded = findViewById(R.id.recyclerviewAdded);
            ActivityRecycleViewDataAdapterAdded addedActivityAdapter = new ActivityRecycleViewDataAdapterAdded(list,list,noSelectedActivity);
            activityRecyclerViewAdded.setAdapter(addedActivityAdapter);
            ViewCompat.setNestedScrollingEnabled(activityRecyclerViewAdded, false);
            GridLayoutManager gridLayoutManagerAdded = new GridLayoutManager(this, 1);
            activityRecyclerViewAdded.setLayoutManager(gridLayoutManagerAdded);

            RecyclerView activityRecyclerView = findViewById(R.id.recyclerview);
            ActivityRecycleViewDataAdapterButton tripDataAdapter = new ActivityRecycleViewDataAdapterButton(
                    noSelectedActivity,allActivities,list,addedActivityAdapter);
            activityRecyclerView.setAdapter(tripDataAdapter);
            ViewCompat.setNestedScrollingEnabled(activityRecyclerView, false);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
            activityRecyclerView.setLayoutManager(gridLayoutManager);

            addedActivityAdapter.setOtherRecycler(tripDataAdapter);
        });


        findViewById(R.id.doneButton).setOnClickListener(v -> {
            Intent result = new Intent();
            result.putExtra("list",list);
            setResult(1,result);
            finish();
        });
    }

}
