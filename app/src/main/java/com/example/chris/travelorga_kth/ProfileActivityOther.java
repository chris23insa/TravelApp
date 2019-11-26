package com.example.chris.travelorga_kth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chris.travelorga_kth.base_component.Participants;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivityOther extends AppCompatActivity {

    private BottomNavigationView mNavigation;
    private Button mEditProfileButton;
    private Participants currentUser;

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
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_other);
        setTitle("Profile");
        setupNavigation();
        currentUser = MainActivity.currentUser;
        ((TextView)findViewById(R.id.profile_name_text)).setText(currentUser.getFirstName() + "  " + currentUser.getLastName());
        ((TextView)findViewById(R.id.description_textview)).setText(currentUser.getDescription());
        CircleImageView imageProfile =currentUser.getProfileImage();
        Log.d("a",currentUser + "   " + imageProfile);
        ((ConstraintLayout)findViewById(R.id.profile_pic)).addView(imageProfile);

        imageProfile.getLayoutParams().height = 300;
        imageProfile.getLayoutParams().width = 300;

        LinearLayout friendsLayout = findViewById(R.id.profile_friends_list_layout);

        for(Participants friends : currentUser.getFriends()){
            LinearLayout newLayout = new LinearLayout(this);
            newLayout.setOrientation(LinearLayout.VERTICAL);
            CircleImageView imageProfileFriend = friends.getProfileImage();
            newLayout.addView(imageProfileFriend);
            imageProfileFriend.getLayoutParams().height = 200;
            imageProfileFriend.getLayoutParams().width = 200;
            TextView name = new TextView(this);
            name.setText(friends.getFirstName());
            newLayout.addView(name);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(40,0,0,0);
            newLayout.setLayoutParams(lp);
            friendsLayout.addView(newLayout);
        }

        Button addFriend = findViewById(R.id.buttonAddFriend);
    }



    private void setupNavigation(){
        //Bottom navigation view
        mNavigation = findViewById(R.id.activity_profile_bottom_navigation);
        BottomNavigationViewHelper.removeShiftMode(mNavigation);
        //Ugly hack to update the selected navbutton
        mNavigation.setSelectedItemId(R.id.action_profile);

        //mNavigation.getMenu().getItem(R.id.action_profile).set
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Edit Profile Button
        mEditProfileButton = findViewById(R.id.edit_profile_button);
        mEditProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivityOther.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mNavigation.setOnNavigationItemReselectedListener(null);
        mNavigation.setSelectedItemId(R.id.action_profile);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
