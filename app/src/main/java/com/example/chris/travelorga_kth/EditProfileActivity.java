package com.example.chris.travelorga_kth;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class EditProfileActivity extends AppCompatActivity {

    private BottomNavigationView mNavigation;
    private Button mSaveButton;
    private Button mCancelButton;

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.action_trips:
                        Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                    case R.id.action_search:
                        Intent intentSearch = new Intent(EditProfileActivity.this, SearchActivity.class);
                        startActivity(intentSearch);
                        finish();
                        return true;
                    case R.id.action_profile:
                        Intent intentProfile = new Intent(EditProfileActivity.this, ProfileActivity.class);
                        startActivity(intentProfile);
                        finish();
                        return true;
                    case R.id.action_map:
                        Intent intentMap = new Intent(EditProfileActivity.this, MapsActivity.class);
                        startActivity(intentMap);
                        finish();
                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setTitle("Edit Profile");


        //Bottom navigation view
        mNavigation = findViewById(R.id.activity_editprofile_navigation);

        //Ugly hack to update the selected navbutton
        mNavigation.setSelectedItemId(R.id.action_profile);
        //mNavigation.getMenu().getItem(R.id.action_profile).set
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Edit Profile Button
        mSaveButton = findViewById(R.id.edit_profile_save_button);
        mSaveButton.setOnClickListener(view ->{
            MainActivity.currentUser.setUsername(((EditText)findViewById(R.id.edit_username)).getText().toString());
            startActivity(new Intent(this, ProfileActivity.class));
            finish();
        });
        //Edit Profile Button
        mCancelButton = findViewById(R.id.edit_cancel_button);
        mCancelButton.setOnClickListener(view -> finish());
    }

}
