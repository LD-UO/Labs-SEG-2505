package com.example.myapplicationtutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Welcomeback extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomeback2);
        Intent intent = getIntent();
        String userType = intent.getStringExtra("usertype");
        //TextView welcomeBackMessage = (TextView) findViewById(R.id.signedin_chefclient);
        TextView subheading = (TextView) findViewById(R.id.signedin_chefclient);
        subheading.setText("Welcome! You are signed in as a " + userType);

    }

    public void logoff(){
        finish();
    }
}