package com.example.chris.travelorga_kth.CreateActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.chris.travelorga_kth.Login;
import com.example.chris.travelorga_kth.R;
import com.example.chris.travelorga_kth.base_component.TripActivity;
import com.example.chris.travelorga_kth.network.ActivityModel;
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

        ArrayList<TripActivity> currentActivity = (ArrayList<TripActivity>)getIntent().getExtras().get("list");

        Scalingo.getInstance().getActivityDao().retrieveAll(
                listA ->{
                    Log.w("aa",listA.toString());
            ArrayList<TripActivity> allActivities = listA.stream().map(ActivityModel::toActivity).collect(Collectors.toCollection(ArrayList::new));

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
    }

}
