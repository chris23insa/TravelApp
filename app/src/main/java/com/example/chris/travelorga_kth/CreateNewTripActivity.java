package com.example.chris.travelorga_kth;
import android.content.Intent;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.chris.travelorga_kth.base_component.Participants;
import com.example.chris.travelorga_kth.base_component.Preference;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateNewTripActivity extends AppCompatActivity {

    private FloatingActionButton addParticipantButton = null;
    private EditText budgetInput = null;
    private Spinner  preferenceInput = null;
    private Button   addActivityButton = null;
    private Button  doneButton = null;
    private BottomNavigationView mNavigation;

    private ArrayList<Participants> participantsTrip;
    private ArrayList<Participants> currentList;
    LinearLayout friends;

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
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_trip);

        addParticipantButton = findViewById(R.id.addingParticipantButton);
        participantsTrip  = new ArrayList<>();
        currentList = new ArrayList<>();
        friends = findViewById(R.id.friendLinear);
        addParticipantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateNewTripActivity.this,SearchParticipantsActivity.class);
                Log.d("aaaaaa",participantsTrip.toString());
                intent.putExtra("list",participantsTrip);
                startActivityForResult(intent,1);
            }
        });

        budgetInput = findViewById(R.id.budgetInput);
        preferenceInput = findViewById(R.id.preferenceInput);
        addActivityButton = findViewById(R.id.addActivityButton);
        doneButton = findViewById(R.id.doneButton);

        preferenceInput.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, Preference.values()));

        doneButton.setOnClickListener(
                view -> finish()
        );

        //Bottom navigation view
        mNavigation = findViewById(R.id.activity_editprofile_navigation);

        //Ugly hack to update the selected navbutton
        mNavigation.setSelectedItemId(R.id.action_profile);
        //mNavigation.getMenu().getItem(R.id.action_profile).set
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        participantsTrip = (ArrayList<Participants>)data.getExtras().get("list");
        Log.d("DEBUG_END",participantsTrip.toString());
        Log.d("resume","aaa");
        Log.d("Resume",participantsTrip.toString());

        for(Participants p : participantsTrip){
            boolean tmp =false;
            for(Participants currentL : currentList){
                if(p.getUsername().equals(currentL.getUsername()))
                 tmp = true;
            }
            if(!tmp){
                currentList.add(p);
                p.setContext(this);
                friends.addView(p.getProfileImage());
            }
        }


     /*   for(Participants p : participantsTrip){
            if(!currentList.contains(p)){
                currentList.add(p);
                p.setContext(this);
                friends.addView(p.getProfileImage());
            }
        }*/
        ArrayList<Participants> tmpCurrent = new ArrayList<>();
        tmpCurrent.addAll(currentList);

    /*    for(Participants p : currentList){
            if(!participantsTrip.contains(p)){
                tmpCurrent.remove(p);
                p.setContext(this);
                friends.removeView(p.getProfileImage());
            }
        }*/
        for(Participants p : currentList){
            boolean temp = false;
            for(Participants tmp : participantsTrip){
                if(p.getUsername().equals(tmp.getUsername())){
                    temp = true;
                }
            }
            if(!temp){
                tmpCurrent.remove(p);
                p.setContext(this);
             //   friends.removeView(p.getProfileImage());
            }
        }

        currentList = tmpCurrent;
        friends.removeAllViews(); //need premade image to change that
        friends.addView(addParticipantButton);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.height =150;
        params.width=150;
        params.gravity = Gravity.CENTER_VERTICAL;

        for(Participants el : currentList){
            CircleImageView image = el.getProfileImage();
            friends.addView(image);
            image.setForegroundGravity(Gravity.CENTER_VERTICAL);
            image.setLayoutParams(params);

        }
    }
}
