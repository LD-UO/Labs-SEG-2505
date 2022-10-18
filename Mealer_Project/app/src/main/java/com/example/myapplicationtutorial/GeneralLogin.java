package com.example.myapplicationtutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
    import android.widget.EditText;
import android.widget.Toast;

public class GeneralLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_login);


        Button loginButton = (Button)findViewById(R.id.login3);

        Button createchef = (Button)findViewById(R.id.createchef);

        Button createclient = (Button)findViewById(R.id.createclient);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText username = (EditText) findViewById(R.id.Login_Username);
                EditText password = (EditText) findViewById(R.id.Login_Password);
                if (username.getText().toString().equals("") || password.getText().toString().equals("")){
                    Log.d("TAG","Username or Password cannot be empty");
                    //Toast.makeText(GeneralLogin.this, "Username or Password cannot be empty", Toast.LENGTH_LONG).show();
                } else{
                    // "The code below will need to be implemented correctly"
                    //Intent intent = new Intent(GeneralLogin.this, WelcomebackAdminActivity.class);
                    // Will need to change the text here to reflect database access!
                    //intent.putExtra("userType", "Undecided");
                    //startActivity(intent);
                }

            }
        });

        createchef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chef_intent = new Intent(GeneralLogin.this,NewChef.class);
                startActivity(chef_intent);

            }


        });

        createclient.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent cook_intent = new Intent(GeneralLogin.this,NewClient.class);
                startActivity(cook_intent);

            }
        });

    }
}