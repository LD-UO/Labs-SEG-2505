package com.example.myapplicationtutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Welcomeback_chef extends AppCompatActivity {
    Button menu_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomeback2);
        Intent intent = getIntent();
        menu_btn = (Button) findViewById(R.id.Menu);
        ImageView logoff = (ImageView) findViewById(R.id.imageView6);
        boolean isBanned = false;
        String endDate = intent.getStringExtra("banned");

        // This means that the user is banned
        if (endDate != null){
            isBanned = true;
            TextView subheading = (TextView) findViewById(R.id.signedin_chefclient);

            if (endDate.equals("indefinitely")){
                subheading.setText("You have been banned indefinitely");
            } else {
                subheading.setText("You are banned until " + endDate);
            }
        }



        // If the user is not banned, it should theoretically set the onclick listener
        if (!isBanned) {
            menu_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //need to change this to access the menu page for a specific chef
                    Intent menu_page_intent = new Intent(Welcomeback_chef.this, MealPage.class);
                    startActivity(menu_page_intent);
                }
            });
        }

        // Users should be able to log off regardless of if they're banned or not
        logoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoff();
            }
        });


    }

    // Finish the intent once the user clicks on the logoff button
    public void logoff(){
        finish();
    }
}