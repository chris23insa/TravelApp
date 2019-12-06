package com.example.chris.travelorga_kth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.example.chris.travelorga_kth.network.Scalingo;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Scalingo.init(this);
        setContentView(R.layout.activity_login);

        String mail = ((EditText)findViewById(R.id.editmail)).getText().toString();
        String password = ((EditText)findViewById(R.id.editPassword)).getText().toString();
        findViewById(R.id.connectButton).setOnClickListener(v -> {
            if(mail.equals("")) {
                Scalingo.getInstance().authenticate(mail, password,
                        response -> startActivity(new Intent(Login.this, MainActivity.class)),
                        null
                );
            }else{
                Scalingo.getInstance().authenticate("moustic@mail.com", "qwerty",
                        response -> startActivity(new Intent(Login.this, MainActivity.class)),
                        null
                );
            }
        });
    }
}
