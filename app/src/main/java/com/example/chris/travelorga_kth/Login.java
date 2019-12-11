package com.example.chris.travelorga_kth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.example.chris.travelorga_kth.network.Scalingo;

import org.json.JSONObject;

public class Login extends AppCompatActivity {
    private static final String currentUserName = "groland@mail.com";
    private static final String currentUserPassword = "qwerty";
    public static long currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Scalingo.init(this);
        setContentView(R.layout.activity_login);
        ((EditText) findViewById(R.id.editmail)).setText(currentUserName);
        ((EditText) findViewById(R.id.editPassword)).setText(currentUserPassword);

        String mail = ((EditText) findViewById(R.id.editmail)).getText().toString();
        String password = ((EditText) findViewById(R.id.editPassword)).getText().toString();
        findViewById(R.id.connectButton).setOnClickListener(v -> {
            if (!mail.equals("")) {
                Scalingo.getInstance().authenticate(mail, password,
                        response -> {
                    try {
                        currentUserId = ((JSONObject) response.get("user")).getLong("id");
                    }catch (Exception ignored){}
                            startActivity(new Intent(Login.this, MainActivity.class));
                        },
                        null
                );
            } else {
                Scalingo.getInstance().authenticate(currentUserName, currentUserPassword,
                        response -> {
                            try {
                                currentUserId = ((JSONObject) response.get("user")).getLong("id");
                            }catch (Exception ignored){}
                            startActivity(new Intent(Login.this, MainActivity.class));
                        },
                        null
                );
            }
        });
    }
}
