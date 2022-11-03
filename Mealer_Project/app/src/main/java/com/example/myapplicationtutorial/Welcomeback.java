package com.example.myapplicationtutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Welcomeback extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomeback2);
        Intent intent = getIntent();

        // This will try and print the user that logs in if they are not banned!
        try {
            String userType = intent.getStringExtra("usertype");
            // This is used to detect if the user has been banned or not
            Log.d("USERTYPE", userType);
            TextView subheading = (TextView) findViewById(R.id.signedin_chefclient);
            subheading.setText("Welcome! You are signed in as a " + userType);
        } catch (Exception e){
            // This should theoretically only run if the chef is banned
            String endDate = intent.getStringExtra("banned");

            TextView subheading = (TextView) findViewById(R.id.signedin_chefclient);

            if (endDate.equals("indefinitely")){
                subheading.setText("You have been banned indefinitely");
            } else {
                subheading.setText("You are banned until " + endDate);
            }
        }



    }

    public void logoff(){
        finish();
    }
}