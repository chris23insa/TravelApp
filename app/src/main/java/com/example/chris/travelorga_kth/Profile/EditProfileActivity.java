package com.example.chris.travelorga_kth.Profile;

import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.chris.travelorga_kth.helper.BottomNavigationViewHelper;
import com.example.chris.travelorga_kth.MainActivity;
import com.example.chris.travelorga_kth.R;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setTitle("Edit Profile");


        //Edit Profile Button
        Button mSaveButton = findViewById(R.id.edit_profile_save_button);
        mSaveButton.setOnClickListener(view ->{
            MainActivity.currentUser.setUsername(((EditText)findViewById(R.id.edit_username)).getText().toString());
            startActivity(new Intent(this, ProfileActivity.class));
            finish();
        });
        //Edit Profile Button
        Button mCancelButton = findViewById(R.id.edit_cancel_button);
        mCancelButton.setOnClickListener(view -> finish());

        BottomNavigationViewHelper.setupNavFinish(this,R.id.action_map);
    }

}
