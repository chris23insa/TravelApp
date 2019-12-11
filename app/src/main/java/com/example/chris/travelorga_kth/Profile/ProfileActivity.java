package com.example.chris.travelorga_kth.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chris.travelorga_kth.MainActivity;
import com.example.chris.travelorga_kth.R;
import com.example.chris.travelorga_kth.base_component.Participants;
import com.example.chris.travelorga_kth.helper.BottomNavigationViewHelper;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressWarnings("ALL")
public class ProfileActivity extends AppCompatActivity {

    private BottomNavigationView mNavigation;
    private Button mEditProfileButton;
    private Participants currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle("Profile");
        setupNavigation();
        currentUser = MainActivity.currentUser;
        ((TextView) findViewById(R.id.profileName)).setText(currentUser.getUsername());
        ((TextView) findViewById(R.id.description)).setText(currentUser.getDescription());
        CircleImageView imageProfile = currentUser.getProfileImage(this);
        ((ConstraintLayout) findViewById(R.id.profile_pic)).addView(imageProfile);

        imageProfile.getLayoutParams().height = 300;
        imageProfile.getLayoutParams().width = 300;

        LinearLayout friendsLayout = findViewById(R.id.friendsLinearList);
        currentUser.getFriends(list -> {
            for (Participants friends : list) {
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
                lp.setMargins(10, 40, 40, 20);
                newLayout.setLayoutParams(lp);
                imageProfileFriend.getLayoutParams().height = 200;
                imageProfileFriend.getLayoutParams().width = 200;

                friendsLayout.addView(newLayout);
            }
        });

    }


    private void setupNavigation() {
        //Bottom navigation view
        BottomNavigationViewHelper.setupNav(this,R.id.action_profile);
        //Edit Profile Button
        mEditProfileButton = findViewById(R.id.edit_profile_button);
        mEditProfileButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            startActivity(intent);
        });
    }
}
