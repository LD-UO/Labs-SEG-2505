package com.example.myapplicationtutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Welcomeback extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomeback2);
        Intent intent = getIntent();
        String userType = intent.getStringExtra("userType");
        TextView welcomeBackMessage = (TextView) findViewById(R.id.textView2);
        TextView subheading = (TextView) findViewById(R.id.textView8);
        subheading.setText("You are signed in as a " + userType);


    }
}