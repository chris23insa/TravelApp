package com.example.chris.travelorga_kth.Profile;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.chris.travelorga_kth.MainActivity;
import com.example.chris.travelorga_kth.R;
import com.example.chris.travelorga_kth.base_component.Participants;
import com.example.chris.travelorga_kth.helper.BottomNavigationViewHelper;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivityOther extends AppCompatActivity {

    private Participants currentProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_other);
        setTitle("Profile");
        setupNavigation();
            currentProfile = (Participants)getIntent().getExtras().get("participant");

        ((TextView)findViewById(R.id.profileName)).setText(currentProfile.getUsername());
        ((TextView)findViewById(R.id.description)).setText(currentProfile.getDescription());
        CircleImageView imageProfile = currentProfile.getProfileImage(this);
        ((ConstraintLayout)findViewById(R.id.profile_pic)).addView(imageProfile);

        imageProfile.getLayoutParams().height = 300;
        imageProfile.getLayoutParams().width = 300;

        LinearLayout friendsLayout = findViewById(R.id.friendsLinearList);

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
        BottomNavigationViewHelper.setupNav(this,R.id.action_profile);
    }

}
