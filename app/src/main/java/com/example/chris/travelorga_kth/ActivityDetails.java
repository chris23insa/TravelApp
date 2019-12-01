package com.example.chris.travelorga_kth;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chris.travelorga_kth.base_component.TripActivity;

public class ActivityDetails extends AppCompatActivity {

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
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
                    Intent intentMain = new Intent(ActivityDetails.this, MainActivity.class);
                    startActivity(intentMain);
                    return true;
                case R.id.action_search:
                    Intent intentSearch = new Intent(ActivityDetails.this, SearchActivity.class);
                    startActivity(intentSearch);
                                        return true;
                case R.id.action_profile:
                   Intent intentProfile = new Intent(ActivityDetails.this, ProfileActivity.class);
                    startActivity(intentProfile);

                    return true;
                case R.id.action_map:
                    Intent intentMap = new Intent(ActivityDetails.this, MapsActivity.class);
                    startActivity(intentMap);

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        TripActivity activity = (TripActivity)getIntent().getExtras().get("activity");
        ((ImageView)findViewById(R.id.toolbarImage)).setImageResource(activity.getImageId());

        ((TextView)findViewById(R.id.descriptionActivityContent)).setText(activity.description);
        ((TextView)findViewById(R.id.openingHoursActivityContent)).setText(activity.getOpeningHour().toString());
        ((TextView)findViewById(R.id.pricesActivityContent)).setText(activity.getPrice().toString());
        ((TextView)findViewById(R.id.bulletPointsActivityContent)).setText(activity.getBulletPoint().toString());
        // Bottom navigation view
        BottomNavigationView navigation = findViewById(R.id.activity_details_bottom_navigation);
        BottomNavigationViewHelper.removeShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
