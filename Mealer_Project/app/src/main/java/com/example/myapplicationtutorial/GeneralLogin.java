package com.example.myapplicationtutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GeneralLogin extends AppCompatActivity {

    DatabaseReference chefReference;
    DatabaseReference clientReference;
    List<String> chefUsername;
    List<String> clientUsername;
    List<String> chefPass;
    List<String> clientPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_login);

        chefReference = FirebaseDatabase.getInstance().getReference("Chef");
        clientReference = FirebaseDatabase.getInstance().getReference("Client");

        chefPass = new ArrayList<String>();
        chefUsername = new ArrayList<String>();
        clientPass = new ArrayList<String>();
        clientUsername = new ArrayList<String>();

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
                    if (username.getText().toString().trim().equals("admin") && password.getText().toString().trim().equals("543")){
                        // Start intent for the admin
                        Log.d("SIGNIN", "Admin was able to sign in");
                    }

                    for (String userNameToCheck : clientUsername){
                        if (userNameToCheck.equals(username.getText().toString().trim())){
                            if (clientPass.get(clientUsername.indexOf(userNameToCheck)).equals(password.getText().toString().trim())){
                                // Start intent for the client
                                Log.d("SIGNIN", "Client was able to sign in");
                            }

                            // Display snackbar for incorrect password
                            Log.d("SIGNIN", "Incorrect password or username");
                        }
                    }

                    for (String userNameToCheck : chefUsername){
                        if (userNameToCheck.equals(username.getText().toString().trim())){
                            if (chefPass.get(chefUsername.indexOf(userNameToCheck)).equals(password.getText().toString().trim())){
                                // Start intent for the client
                                Log.d("SIGNIN", "Chef was able to sign in");


                            }

                            // Display snackbar for incorrect password, PUT THIS STATEMENT IN AN ELSE
                            Log.d("SIGNIN", "Incorrect password or username");
                        }
                    }

                    // If the username cannot be found which occurs if neither of the statements above run, then username
                    // does not exist
                    // Will put a snackbar here
                    // Implement admin sign in feature
                    Log.d("SIGNIN", "User does not exist");
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

    protected void onStart(){
        super.onStart();
        chefReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chefUsername.clear();
                chefPass.clear();
                for (DataSnapshot chefShot : snapshot.getChildren()){
                    // Copies two of them for some reason
                    String username = chefShot.child("username").getValue().toString();
                    String password = chefShot.child("password").getValue().toString();
                    chefUsername.add(username);
                    chefPass.add(password);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        clientReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clientUsername.clear();
                clientPass.clear();
                for (DataSnapshot clientShot : snapshot.getChildren()){
                    // Copies two of them for some reason
                    String username = clientShot.child("username").getValue().toString();
                    String password = clientShot.child("password").getValue().toString();
                    clientUsername.add(username);
                    clientPass.add(password);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}