package com.example.myapplicationtutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Welcomeback extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomeback2);
        Intent intent = getIntent();
        String userType = intent.getStringExtra("usertype");
        //TextView welcomeBackMessage = (TextView) findViewById(R.id.textView2);
        //TextView subheading = (TextView) findViewById(R.id.textView8);
        //subheading.setText("Welcome! You are signed in as a " + userType);

    }

    public void logoff(){
        finish();
    }
}