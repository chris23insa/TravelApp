package com.example.chris.travelorga_kth;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chris.travelorga_kth.base_component.TripActivity;
import com.example.chris.travelorga_kth.helper.BottomNavigationViewHelper;

public class ActivityDetails extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

         long  activityID = (long)getIntent().getExtras().get("id");
        TripActivity.findBydId(activityID, this, activity ->{
            ImageView image = findViewById(R.id.toolbarImage);
            Glide.with(image).load(activity.getImage()).apply(MainActivity.glideOption).into(image);
            ((TextView)findViewById(R.id.descriptionActivityContent)).setText(activity.description);
            ((TextView)findViewById(R.id.openingHoursActivityContent)).setText(activity.getOpeningHour());
            ((TextView)findViewById(R.id.pricesActivityContent)).setText(activity.getPrice());
            ((TextView)findViewById(R.id.bulletPointsActivityContent)).setText(activity.getBulletPoint());
        });
        BottomNavigationViewHelper.setupNav(this,R.id.action_trips);
    }
}
