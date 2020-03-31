package com.example.deneme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private TextView Username , Email;
    private Button logoutButton;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        Username = findViewById(R.id.username);
        Email = findViewById(R.id.email);
        logoutButton = findViewById(R.id.logoutButton);


        HashMap<String, String> user = sessionManager.getUserDetail();
        String mUsername = user.get(sessionManager.USERNAME);
        String mEmail = user.get(sessionManager.EMAIL);

        Username.setText(mUsername);
        Email.setText(mEmail);


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
            }
        });


    }
}
