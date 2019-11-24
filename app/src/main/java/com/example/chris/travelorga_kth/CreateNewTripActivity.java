package com.example.chris.travelorga_kth;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class CreateNewTripActivity extends AppCompatActivity {

    private FloatingActionButton addParticipantButton = null;
    private EditText budgetInput = null;
    private Spinner  preferenceInput = null;
    private Button   addActivityButton = null;
    private Button  doneButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_trip);

        addParticipantButton = findViewById(R.id.addingParticipantButton);
        budgetInput = findViewById(R.id.budgetInput);
        preferenceInput = findViewById(R.id.preferenceInput);
        addActivityButton = findViewById(R.id.addActivityButton);
        doneButton = findViewById(R.id.doneButton);

    }
}
