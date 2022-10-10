package com.example.myapplicationtutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Welcomeback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomeback2);
        Intent intent = getIntent();
        String userType = intent.getStringExtra("userType");
        TextView welcomeBackMessage = (TextView) findViewById(R.id.textView2);
        welcomeBackMessage.setText("Welcome! You are logged in as " + userType);
    }
}