package com.example.chris.travelorga_kth.CreateActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;

import com.example.chris.travelorga_kth.R;
import com.example.chris.travelorga_kth.base_component.TripActivity;
import com.example.chris.travelorga_kth.helper.BottomNavigationViewHelper;
import com.example.chris.travelorga_kth.recycler_view_list_activities.ActivityRecycleViewDataAdapterAdded;
import com.example.chris.travelorga_kth.recycler_view_list_activities.ActivityRecycleViewDataAdapterButton;

import java.util.ArrayList;

public class SearchTripActivityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trip_activity);
        ArrayList<TripActivity> currentActivity = (ArrayList<TripActivity>) getIntent().getExtras().get("list");

        TripActivity.getALL(this,
                allActivities -> process(filter(allActivities), currentActivity));

        findViewById(R.id.doneButton).setOnClickListener(v -> {
            Intent result = new Intent();
            result.putExtra("list", currentActivity);
            setResult(1, result);
            finish();
        });
        BottomNavigationViewHelper.setupNav(this,R.id.action_search);  }

    private void process(ArrayList<TripActivity> allActivities, ArrayList<TripActivity> currentActivity) {
        ArrayList<TripActivity> noSelectedActivity = new ArrayList<>(allActivities);
        noSelectedActivity.removeAll(currentActivity);

        RecyclerView activityRecyclerViewAdded = findViewById(R.id.recyclerviewAdded);
        ActivityRecycleViewDataAdapterAdded addedActivityAdapter = new ActivityRecycleViewDataAdapterAdded(currentActivity);
        addedActivityAdapter.addList(noSelectedActivity);
        activityRecyclerViewAdded.setAdapter(addedActivityAdapter);
        ViewCompat.setNestedScrollingEnabled(activityRecyclerViewAdded, false);
        GridLayoutManager gridLayoutManagerAdded = new GridLayoutManager(this, 1);
        activityRecyclerViewAdded.setLayoutManager(gridLayoutManagerAdded);

        RecyclerView activityRecyclerView = findViewById(R.id.recyclerview);
        ActivityRecycleViewDataAdapterButton tripDataAdapter = new ActivityRecycleViewDataAdapterButton(
                noSelectedActivity);
        tripDataAdapter.addRecyler(addedActivityAdapter);
        tripDataAdapter.addList(allActivities, currentActivity);
        activityRecyclerView.setAdapter(tripDataAdapter);
        ViewCompat.setNestedScrollingEnabled(activityRecyclerView, false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        activityRecyclerView.setLayoutManager(gridLayoutManager);

        addedActivityAdapter.setOtherRecycler(tripDataAdapter);

        addedActivityAdapter.setOtherRecycler(tripDataAdapter);
    }

    private ArrayList<TripActivity> filter(ArrayList<TripActivity> l) {
        SearchView view = findViewById(R.id.search_view);
        String query = view.getQuery().toString().toLowerCase();
        ArrayList<TripActivity> tmp = new ArrayList<>();
        for (TripActivity a : l) {
            if (a.description.contains(query) || a.place.contains(query) || a.getName().contains(query))
                tmp.add(a);
        }
        return tmp;
    }
}
