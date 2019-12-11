package com.example.chris.travelorga_kth.CreateActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.chris.travelorga_kth.ComputedTrip;
import com.example.chris.travelorga_kth.Login;
import com.example.chris.travelorga_kth.R;
import com.example.chris.travelorga_kth.base_component.Participants;
import com.example.chris.travelorga_kth.base_component.Preference;
import com.example.chris.travelorga_kth.base_component.Trip;
import com.example.chris.travelorga_kth.base_component.TripActivity;
import com.example.chris.travelorga_kth.helper.BottomNavigationViewHelper;
import com.example.chris.travelorga_kth.helper.DateUtil;
import com.example.chris.travelorga_kth.network.TripModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateNewTripActivity extends AppCompatActivity {

    private FloatingActionButton addParticipantButton = null;
    private EditText budgetInput = null;
    private Button addActivityButton = null;

    private ArrayList<Participants> currentParticipantList = new ArrayList<>();
    private ArrayList<TripActivity> currentActivitiesList= new ArrayList<>();
    private ArrayList<TripActivity> activitiesTrip= new ArrayList<>();
    private LinearLayout friends;
    private LinearLayout activities;
    private EditText tripName;
    private Preference selectedPreference;
    private EditText description;
    private EditText place;
    private DatePicker dateFrom;
    private  DatePicker dateTo;

    private final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_trip);

        getInputView();

        params.height = 150;
        params.width = 150;
        params.gravity = Gravity.CENTER_VERTICAL;

        addParticipantButton.setOnClickListener(v -> {
            Intent intent = new Intent(CreateNewTripActivity.this, SearchParticipantsActivity.class);
            intent.putExtra("list", currentParticipantList);
            startActivityForResult(intent, 1);
        });

        if (getIntent().getExtras() != null) {
            Trip t = (Trip) getIntent().getExtras().get("trip");
                getDataTrip(t);
        }


       Button doneButton = findViewById(R.id.doneButton);
        doneButton.setOnClickListener(
                view ->  addTheTrip()
        );

        addActivityButton.setOnClickListener(v -> {
            Intent intent = new Intent(CreateNewTripActivity.this, SearchTripActivityActivity.class);
            intent.putExtra("list", currentActivitiesList);
            startActivityForResult(intent, 2);
        });


        BottomNavigationViewHelper.setupNav(this,R.id.action_profile);
          }

    private void getInputView(){
        budgetInput = findViewById(R.id.budgetInput);
        Spinner preferenceInput = findViewById(R.id.preferenceInput);
        addActivityButton = findViewById(R.id.addActivityButton);

        tripName = findViewById(R.id.name);
        dateFrom = findViewById(R.id.dateFrom);
        dateTo = findViewById(R.id.dateTo);
        description = findViewById(R.id.description);
        place = findViewById(R.id.place);

        addParticipantButton = findViewById(R.id.addingParticipantButton);
        friends = findViewById(R.id.friendLinear);
        activities = findViewById(R.id.activities);

        preferenceInput.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPreference = (Preference) parent.getSelectedItem();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        preferenceInput.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, Preference.values()));
    }


        doneButton.setOnClickListener(view -> {
                    try {
                        Log.d("Date", getDateFromDatePicker(dateFrom).toString() + "  " + (budgetInput.getText().toString()));
                        TripModel m = new TripModel(Login.currentUserId, tripName.getText().toString(),
                                place.getText().toString(), ""
                                , description.getText().toString(), Integer.parseInt(budgetInput.getText().toString()),
                                selectedPreference,
                                0, 0, getDateFromDatePicker(dateFrom), getDateFromDatePicker(dateTo));
                        Log.d("ERROR", m.toString());
                        Scalingo.getInstance().getTripDao().create(m,
                                trip -> {
                                    Log.d("TIP", trip.toString());
                                    for (TripActivity tp : currentActivitiesList) {
                                        try {
                                            trip.toTrip(this).addActivity(tp.getId(), tp.getFrom(), tp.getTo());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    // TODO : Compute the trip and then print the different information about it
                                    Intent intent = new Intent(CreateNewTripActivity.this, ComputedTrip.class);
                                    startActivity(intent);
                                    finish();
                                }, error -> error.printStackTrace());
                    } catch (Exception e) {
                        doneButton.setText("Incorrect value");
                        e.printStackTrace();
                    }

    private void getDataTrip(Trip t){
        tripName.setText(t.getTripName());
        description.setText(t.getTripDescription());
        place.setText(t.getPlace());
        t.getListActivity(list -> {
            for (TripActivity el : list) {
                if (!currentActivitiesList.contains(el)) {
                    currentActivitiesList.add(el);
                    CircleImageView image = el.getImageCircle(this);
                    activities.addView(image);
                    image.setForegroundGravity(Gravity.CENTER_VERTICAL);
                    image.setLayoutParams(params);

               }
            }
        }, this);
        //todo add place to database
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            participantResult(data);
        } else if (requestCode == 2) {
            activityResult(data);
        }
    }

    private void participantResult(Intent data) {
        if (data.getExtras() == null)
            return;
        ArrayList<Participants> participantList = (ArrayList<Participants>) data.getExtras().get("list");

        currentParticipantList.removeAll(currentParticipantList);
        currentParticipantList.addAll(participantList);
        friends.removeAllViews(); //need premade image to change that
        friends.addView(addParticipantButton);

        for (Participants el : currentParticipantList) {
            CircleImageView image = el.getProfileImage(this);
            friends.addView(image);
            image.setForegroundGravity(Gravity.CENTER_VERTICAL);
            image.setLayoutParams(params);
        }
    }

    private void activityResult(Intent data) {
        activitiesTrip = (ArrayList<TripActivity>) data.getExtras().get("list");

        currentActivitiesList.removeAll(currentActivitiesList);
        currentActivitiesList.addAll(activitiesTrip);
        activities.removeAllViews(); //need premade image to change that
        activities.addView(addActivityButton);

        for (TripActivity el : currentActivitiesList) {
            CircleImageView image = el.getImageCircle(this);
            activities.addView(image);
            image.setForegroundGravity(Gravity.CENTER_VERTICAL);
            image.setLayoutParams(params);
        }
    }

    private void addTheTrip(){
            TripModel model = new TripModel(Login.currentUserId, tripName.getText().toString(),
                    place.getText().toString(), ""
                    , description.getText().toString(), Integer.parseInt(budgetInput.getText().toString()),
                    selectedPreference,
                    0, 0,
                    DateUtil.getDateFromDatePicker(dateFrom), DateUtil.getDateFromDatePicker(dateTo));
            Trip.create(model, this,
                    newTrip -> {
                        for (TripActivity tp : currentActivitiesList)
                            newTrip.addActivity(tp.getId(), tp.getDateStringFrom(), tp.getDateStringTo());
                        finish();
                    });
        }
}