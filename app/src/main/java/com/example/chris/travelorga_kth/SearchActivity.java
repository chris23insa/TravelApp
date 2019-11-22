package com.example.chris.travelorga_kth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private BottomNavigationView mNavigation;
    private SearchView mSearchView;
    private ArrayList<TripRecyclerViewItem> mPreviousSearchList = null;

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
                    Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.action_search:
                    return true;
                case R.id.action_profile:
                    intent = new Intent(SearchActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
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
        setContentView(R.layout.activity_search);
        setTitle("Search");

        //Bottom navigation view
        mNavigation = (BottomNavigationView) findViewById(R.id.activity_search_bottom_navigation);
        BottomNavigationViewHelper.removeShiftMode(mNavigation);
        //Ugly hack to update the selected navbutton
        mNavigation.setSelectedItemId(R.id.action_search);

        //mNavigation.getMenu().getItem(R.id.action_profile).set
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        mSearchView = (SearchView) findViewById(R.id.search_view);
        mSearchView.onActionViewExpanded(); //new Added line
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setQueryHint("Enter search...");
        if(!mSearchView.isFocused()) {
            mSearchView.clearFocus();
        }
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        initializeItemList();
        // Create the recyclerview.
        RecyclerView searchRecyclerView = (RecyclerView)findViewById(R.id.previous_searches_recyclerview);
        // Create the grid layout manager with 1 columns.
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        // Set layout manager.
        searchRecyclerView.setLayoutManager(gridLayoutManager);
        //ViewCompat.setNestedScrollingEnabled(searchRecyclerView, false);

        // Create recycler view data adapter with trip item list.
        TripRecyclerViewDataAdapter tripDataAdapter = new TripRecyclerViewDataAdapter(mPreviousSearchList);
        // Set data adapter.
        searchRecyclerView.setAdapter(tripDataAdapter);
    }

    /* Initialise trip items in list. */
    private void initializeItemList()
    {
        if(mPreviousSearchList == null)
        {
            mPreviousSearchList = new ArrayList<TripRecyclerViewItem>();
            mPreviousSearchList.add(new TripRecyclerViewItem("Londres", R.drawable.londres, "17/11/2019", "21/11/2019", "Trip in Londres for 3 days with the best !"));
            mPreviousSearchList.add(new TripRecyclerViewItem("Paris", R.drawable.tour_eiffel, "16/09/2017", "20/09/2017", "Trip in Paris to see the eiffel tower, unbelievable !"));
            mPreviousSearchList.add(new TripRecyclerViewItem("New-York", R.drawable.new_york, "02/03/2019", "10/03/2019", "New-yok, city of light with my partner in crime."));
            mPreviousSearchList.add(new TripRecyclerViewItem("Stockholm", R.drawable.stockholm, "30/04/2019", "05/05/2019", "Lake, Park, Cold, description of our journey."));
        }
    }
}
