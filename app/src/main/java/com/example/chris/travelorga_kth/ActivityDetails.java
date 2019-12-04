package com.example.chris.travelorga_kth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chris.travelorga_kth.base_component.TripActivity;

public class ActivityDetails extends Activity {

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
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
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        TripActivity activity = (TripActivity)getIntent().getExtras().get("activity");
        ((ImageView)findViewById(R.id.toolbarImage)).setImageResource(activity.getImageId());

        ((TextView)findViewById(R.id.descriptionActivityContent)).setText(activity.description);
        ((TextView)findViewById(R.id.openingHoursActivityContent)).setText(activity.getOpeningHour());
        ((TextView)findViewById(R.id.pricesActivityContent)).setText(activity.getPrice());
        ((TextView)findViewById(R.id.bulletPointsActivityContent)).setText(activity.getBulletPoint());
        // Bottom navigation view
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.removeShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
