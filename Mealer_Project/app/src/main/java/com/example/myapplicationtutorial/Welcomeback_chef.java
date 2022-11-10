package com.example.myapplicationtutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Welcomeback_chef extends AppCompatActivity {
    Button menu_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomeback2);
        Intent intent = getIntent();
        menu_btn = (Button) findViewById(R.id.Menu);

        // This will try and print the user that logs in if they are not banned!
        try {
            String userType = intent.getStringExtra("usertype");
            String name = intent.getStringExtra("username");
            // This is used to detect if the user has been banned or not
            Log.d("USERTYPE", userType);
            TextView subheading = (TextView) findViewById(R.id.welcome_chefclient);

            subheading.setText(name);
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
        menu_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //need to change this to access the menu page for a specific chef
                
            }
        });


    }

    public void logoff(){
        finish();
    }
}