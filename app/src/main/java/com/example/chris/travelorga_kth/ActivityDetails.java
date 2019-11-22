package com.example.chris.travelorga_kth;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.content.Intent;

public class ActivityDetails extends AppCompatActivity {

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
                    Intent intent = new Intent(ActivityDetails.this, MainActivity.class);
                    startActivity(intent);
                    finish();
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
        setContentView(R.layout.activity_details);

        // Bottom navigation view
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.activity_details_bottom_navigation);
        BottomNavigationViewHelper.removeShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
}
