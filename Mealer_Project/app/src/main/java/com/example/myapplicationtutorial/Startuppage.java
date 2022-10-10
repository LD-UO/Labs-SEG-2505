package com.example.myapplicationtutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Startuppage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startuppage);
        getSupportActionBar().hide();

        Button chefButton = (Button)findViewById(R.id.createaccbtn2);
        chefButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Startuppage.this,Newchef.class));


            }


        });

        Button clientButton = (Button)findViewById(R.id.createaccbtn);
        clientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Startuppage.this, Newclient.class));
            }
        });

        Button general = (Button)findViewById(R.id.signinbtn);
        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Startuppage.this, GeneralLogin.class));
            }
        });



    }





    }


