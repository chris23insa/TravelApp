package com.example.chris.travelorga_kth;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<TripRecyclerViewItem> carItemList = null;


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
                case R.id.action_trips:
                    return true;
                case R.id.action_search:
                    return true;
                case R.id.action_profile:
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
        setContentView(R.layout.activity_main);

        setTitle("TravelApp");

        initializeCarItemList();

        // Create the recyclerview.
        RecyclerView tripRecyclerView = (RecyclerView)findViewById(R.id.card_view_recycler_list);
        // Create the grid layout manager with 1 columns.
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        // Set layout manager.
        tripRecyclerView.setLayoutManager(gridLayoutManager);

        // Create car recycler view data adapter with trip item list.
        TripRecyclerViewDataAdapter tripDataAdapter = new TripRecyclerViewDataAdapter(carItemList);
        // Set data adapter.
        tripRecyclerView.setAdapter(tripDataAdapter);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.activity_main_bottom_navigation);
        BottomNavigationViewHelper.removeShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    /* Initialise car items in list. */
    private void initializeCarItemList()
    {
        if(carItemList == null)
        {
            carItemList = new ArrayList<TripRecyclerViewItem>();
            carItemList.add(new TripRecyclerViewItem("Londres", R.drawable.londres, "17/11/2019", "21/11/2019"));
            carItemList.add(new TripRecyclerViewItem("Paris", R.drawable.tour_eiffel, "16/09/2017", "20/09/2017"));
            carItemList.add(new TripRecyclerViewItem("New-York", R.drawable.new_york, "02/03/2019", "10/03/2019"));
            carItemList.add(new TripRecyclerViewItem("Stockholm", R.drawable.stockholm, "30/04/2019", "05/05/2019"));
        }
    }

}
