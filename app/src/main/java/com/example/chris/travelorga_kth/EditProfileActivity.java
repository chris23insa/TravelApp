package com.example.chris.travelorga_kth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class EditProfileActivity extends AppCompatActivity {

    private BottomNavigationView mNavigation;
    private Button mSaveButton;
    private Button mCancelButton;

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
                    Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.action_search:
                    return true;
                case R.id.action_profile:
                    return true;
                case R.id.action_map:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setTitle("Edit Profile");


        //Bottom navigation view
        mNavigation = (BottomNavigationView) findViewById(R.id.activity_editprofile_navigation);
        BottomNavigationViewHelper.removeShiftMode(mNavigation);
        //Ugly hack to update the selected navbutton
        mNavigation.setSelectedItemId(R.id.action_profile);
        //mNavigation.getMenu().getItem(R.id.action_profile).set
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Edit Profile Button
        mSaveButton = (Button) findViewById(R.id.edit_profile_save_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //Edit Profile Button
        mCancelButton = (Button) findViewById(R.id.edit_cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}