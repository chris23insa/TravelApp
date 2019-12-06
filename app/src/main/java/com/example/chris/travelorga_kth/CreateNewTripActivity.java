package com.example.chris.travelorga_kth;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.chris.travelorga_kth.base_component.Participants;
import com.example.chris.travelorga_kth.base_component.Preference;
import com.example.chris.travelorga_kth.base_component.TripActivity;
import com.example.chris.travelorga_kth.network.Scalingo;
import com.example.chris.travelorga_kth.network.TripModel;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateNewTripActivity extends AppCompatActivity {

    private FloatingActionButton addParticipantButton = null;
    private EditText budgetInput = null;
    private Button   addActivityButton = null;

    private ArrayList<Participants> currentParticipantList;
    private ArrayList<TripActivity>  currentActivitiesList;
    private ArrayList<TripActivity>  activitiesTrip;
    private LinearLayout friends;
    private LinearLayout activities;
    private EditText tripName;
    private Preference selectedPreference;
    private EditText description;

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.action_trips:
                Intent intent = new Intent(CreateNewTripActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_search:
                Intent intentSearch = new Intent(CreateNewTripActivity.this, SearchActivity.class);
                startActivity(intentSearch);
                finish();
                return true;
            case R.id.action_profile:
                Intent intentProfile = new Intent(CreateNewTripActivity.this, ProfileActivity.class);
                startActivity(intentProfile);
                finish();
                return true;
            case R.id.action_map:
                Intent intentMap = new Intent(CreateNewTripActivity.this, MapsActivity.class);
                startActivity(intentMap);
                finish();
                return true;
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_trip);

        addParticipantButton = findViewById(R.id.addingParticipantButton);
        activitiesTrip = new ArrayList<>();
        currentParticipantList = new ArrayList<>();
        currentActivitiesList = new ArrayList<>();
        friends = findViewById(R.id.friendLinear);
        activities = findViewById(R.id.activities);
        addParticipantButton.setOnClickListener(v -> {
            Intent intent = new Intent(CreateNewTripActivity.this, SearchParticipantsActivity.class);
            intent.putExtra("list", activitiesTrip);
            startActivityForResult(intent, 1);
        });

        budgetInput = findViewById(R.id.budgetInput);
        Spinner preferenceInput = findViewById(R.id.preferenceInput);
        addActivityButton = findViewById(R.id.addActivityButton);
        Button doneButton = findViewById(R.id.doneButton);
        tripName = findViewById(R.id.tripName);
        EditText dateFrom = findViewById(R.id.dateFrom);
        EditText dateTo = findViewById(R.id.dateTo);
        description = findViewById(R.id.description);

        preferenceInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPreference = (Preference)parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        preferenceInput.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, Preference.values()));

        doneButton.setOnClickListener(view -> {
            try {
                //TODO date
                Scalingo.getInstance().getTripDao().create(new TripModel(MainActivity.currentUserId,tripName.getText().toString(),""
                        ,description.getText().toString(),Integer.parseInt(budgetInput.getText().toString()), selectedPreference,
                        0,0,new Date(),new Date()),null,null);
                        /*new Trip(tripName.getText().toString(), 0, dateTo.getText().toString(), dateTo.getText().toString(),
                        description.getText().toString(), activitiesTrip, participantList,
                        Integer.parseInt(budgetInput.getText().toString()), selectedPreference, this);*/

                Intent intent = new Intent(this,MainActivity.class);
                setResult(1,intent);
                finish();
            }catch (Exception ignored){

            }

                }
        );

        addActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(CreateNewTripActivity.this,SearchActivity.class);
            startActivityForResult(intent,2);
        });

        //Bottom navigation view
        BottomNavigationView mNavigation = findViewById(R.id.bottom_navigation);

        //Ugly hack to update the selected navbutton
        mNavigation.setSelectedItemId(R.id.action_profile);
        //mNavigation.getMenu().getItem(R.id.action_profile).set
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == 1){
            participantResult(data);
    }else if(requestCode == 2){
        activityResult(data);
        }
    }

    private void participantResult(Intent data){
        ArrayList<Participants> participantList = (ArrayList<Participants>) data.getExtras().get("list");

        for(Participants p : participantList){
            if(!currentParticipantList.contains(p)){
                currentParticipantList.add(p);
                friends.addView(p.getProfileImage(this));
            }
        }
        ArrayList<Participants> tmpCurrent = new ArrayList<>(currentParticipantList);

        for (Participants p : currentParticipantList) {
            if(!participantList.contains(p)){
                tmpCurrent.remove(p);
                friends.removeView(p.getProfileImage(this));
            }
        }
        currentParticipantList = tmpCurrent;
        friends.removeAllViews(); //need premade image to change that
        friends.addView(addParticipantButton);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.height =150;
        params.width=150;
        params.gravity = Gravity.CENTER_VERTICAL;

        for(Participants el : currentParticipantList){
            CircleImageView image = el.getProfileImage(this);
            friends.addView(image);
            image.setForegroundGravity(Gravity.CENTER_VERTICAL);
            image.setLayoutParams(params);

        }
    }

    private void activityResult(Intent data){
        activitiesTrip = (ArrayList<TripActivity>)data.getExtras().get("list");

        for(TripActivity p : activitiesTrip){
            if(!currentActivitiesList.contains(p)){
                currentActivitiesList.add(p);
                activities.addView(p.getImageCircle(this));
            }
        }
        ArrayList<TripActivity> tmpCurrent = new ArrayList<>(currentActivitiesList);

        for (TripActivity p : currentActivitiesList) {
            if(!activitiesTrip.contains(p)){
                tmpCurrent.remove(p);
                activities.removeView(p.getImageCircle(this));
            }
        }
        currentActivitiesList = tmpCurrent;
        activities.removeAllViews(); //need premade image to change that
        activities.addView(addActivityButton);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.height =150;
        params.width=150;
        params.gravity = Gravity.CENTER_VERTICAL;

        for(TripActivity el : currentActivitiesList){
            CircleImageView image = el.getImageCircle(this);
            activities.addView(image);
            image.setForegroundGravity(Gravity.CENTER_VERTICAL);
            image.setLayoutParams(params);
        }
    }
}
