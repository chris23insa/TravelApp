package com.example.chris.travelorga_kth;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.chris.travelorga_kth.base_component.Participants;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivityOther extends AppCompatActivity {

    private BottomNavigationView mNavigation;
    private Participants currentProfile;

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.action_trips:
                Intent intent = new Intent(ProfileActivityOther.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_search:
                intent = new Intent(ProfileActivityOther.this, SearchActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.action_profile:
                return true;
            case R.id.action_map:
                Intent intentMapActivity = new Intent(ProfileActivityOther.this, MapsActivity.class);
                startActivity(intentMapActivity);
                return true;
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_other);
        setTitle("Profile");
        setupNavigation();
            currentProfile = (Participants)getIntent().getExtras().get("participant");

        ((TextView)findViewById(R.id.profile_name_text)).setText(currentProfile.getUsername());
        ((TextView)findViewById(R.id.description_textview)).setText(currentProfile.getDescription());
        CircleImageView imageProfile = currentProfile.getProfileImage(this);
        ((ConstraintLayout)findViewById(R.id.profile_pic)).addView(imageProfile);

        imageProfile.getLayoutParams().height = 300;
        imageProfile.getLayoutParams().width = 300;

        LinearLayout friendsLayout = findViewById(R.id.profile_friends_list_layout);

        currentProfile.getFriends(list ->{
            for(Participants friends : list){
                LinearLayout newLayout = new LinearLayout(this);
                newLayout.setOrientation(LinearLayout.VERTICAL);
                CircleImageView imageProfileFriend = friends.getProfileImage(this);
                newLayout.addView(imageProfileFriend);

                TextView name = new TextView(this);
                name.setText(friends.getUsername());
                name.setGravity(Gravity.CENTER_HORIZONTAL);
                newLayout.addView(name);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(10,40,40,20);
                newLayout.setLayoutParams(lp);
                imageProfileFriend.getLayoutParams().height = 200;
                imageProfileFriend.getLayoutParams().width = 200;
                friendsLayout.addView(newLayout);
            }
        });


        ToggleButton addFriendsButton = findViewById(R.id.buttonAddFriend);
        MainActivity.currentUser.getFriends(list ->{
            addFriendsButton.setChecked(list.contains(currentProfile));
            if (addFriendsButton.isChecked()) {
                addFriendsButton.setText("Remove from my friends");
                addFriendsButton.setOnClickListener(v -> MainActivity.currentUser.addFriend(currentProfile));
            }else{
                addFriendsButton.setText("Add to my friends");
                addFriendsButton.setOnClickListener(v -> MainActivity.currentUser.removeFriends(currentProfile));
            }
        });


    }



    private void setupNavigation(){
        //Bottom navigation view
        mNavigation = findViewById(R.id.activity_profile_bottom_navigation);

        //Ugly hack to update the selected navbutton
        mNavigation.setSelectedItemId(R.id.action_profile);

        //mNavigation.getMenu().getItem(R.id.action_profile).set
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    protected void onResume() {
        super.onResume();

        mNavigation.setOnNavigationItemReselectedListener(null);
        mNavigation.setSelectedItemId(R.id.action_profile);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
