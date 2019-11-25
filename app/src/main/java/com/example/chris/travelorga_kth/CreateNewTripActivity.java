package com.example.chris.travelorga_kth;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class CreateNewTripActivity extends AppCompatActivity {

    private FloatingActionButton addParticipantButton = null;
    private EditText budgetInput = null;
    private Spinner  preferenceInput = null;
    private Button   addActivityButton = null;
    private Button  doneButton = null;
    private BottomNavigationView mNavigation;

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
        budgetInput = findViewById(R.id.budgetInput);
        preferenceInput = findViewById(R.id.preferenceInput);
        addActivityButton = findViewById(R.id.addActivityButton);
        doneButton = findViewById(R.id.doneButton);

        doneButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                }
        );

        //Bottom navigation view
        mNavigation = (BottomNavigationView) findViewById(R.id.activity_editprofile_navigation);
        BottomNavigationViewHelper.removeShiftMode(mNavigation);
        //Ugly hack to update the selected navbutton
        mNavigation.setSelectedItemId(R.id.action_profile);
        //mNavigation.getMenu().getItem(R.id.action_profile).set
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
}
