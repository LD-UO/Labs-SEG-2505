package com.example.myapplicationtutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class WelcomebackAdminActivity extends AppCompatActivity {

    // We should get rid of this class because we do not need it anymore
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomeback_admin);
    }
}