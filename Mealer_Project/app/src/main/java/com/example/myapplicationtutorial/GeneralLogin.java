package com.example.myapplicationtutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GeneralLogin extends AppCompatActivity {

    DatabaseReference chefReference;
    DatabaseReference clientReference;
    DatabaseReference complaintReference;
    List<String> chefUsername;
    List<String> clientUsername;
    List<String> chefPass;
    List<String> clientPass;
    List<String> complaintUsername;
    List<String> complaintEndDate;
    List<Boolean> complaintAddressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_login);

        chefReference = FirebaseDatabase.getInstance().getReference("Chef");
        clientReference = FirebaseDatabase.getInstance().getReference("Client");
        complaintReference = FirebaseDatabase.getInstance().getReference("Complaint");

        chefPass = new ArrayList<String>();
        chefUsername = new ArrayList<String>();
        clientPass = new ArrayList<String>();
        clientUsername = new ArrayList<String>();

        // List of all the relevant complaint attributes
        complaintUsername = new ArrayList<String>();
        complaintEndDate = new ArrayList<String>();
        complaintAddressed = new ArrayList<Boolean>();


        Button loginButton = (Button)findViewById(R.id.login3);

        Button createchef = (Button)findViewById(R.id.createchef);

        Button createclient = (Button)findViewById(R.id.createclient);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText username = (EditText) findViewById(R.id.Login_Username);
                EditText password = (EditText) findViewById(R.id.Login_Password);
                // This will keep track of whether the account could be found
                boolean loginfound = false;

                if (username.getText().toString().equals("") || password.getText().toString().equals("")){

                    //Toast.makeText(GeneralLogin.this, "Username or Password cannot be empty", Toast.LENGTH_LONG).show();

                } else{
                    if (username.getText().toString().trim().equals("admin") && password.getText().toString().trim().equals("543")){
                        // Start intent for the admin
                        Intent Login = new Intent(GeneralLogin.this, WelcomebackAdminActivity.class);
                        loginfound = true;
                        startActivity(Login);
                        username.setText("");
                        password.setText("");
                    }


                    for (String userNameToCheck : clientUsername){
                        if (userNameToCheck.equals(username.getText().toString().trim())){
                            if (clientPass.get(clientUsername.indexOf(userNameToCheck)).equals(password.getText().toString().trim())){
                                // Start intent for the client
                                Intent Login = new Intent(GeneralLogin.this,Welcomeback_client.class);
                                Login.putExtra("username",username.getText().toString());
                                startActivity(Login);
                                username.setText("");
                                password.setText("");
                                loginfound = true;

                            } else {
                                // Displaying the snackbar for an incorrect password
                                Snackbar incorrect = Snackbar.make(view, "Incorrect Username or Password", Snackbar.LENGTH_LONG);
                                incorrect.show();
                            }


                        }
                    }

                    for (String userNameToCheck : chefUsername){
                        if (userNameToCheck.equals(username.getText().toString().trim())){
                            if (chefPass.get(chefUsername.indexOf(userNameToCheck)).equals(password.getText().toString().trim())){



                                // Start intent for the client
                                // Need to check and see if the client has been banned or not
                                if (complaintUsername.contains(userNameToCheck)){
                                    // This means that there has been a complaint made

                                    // Need to check if it has been addressed or not
                                    if (complaintAddressed.get(complaintUsername.indexOf(userNameToCheck))){
                                        // This means the complaint has been addressed, need to check for the end date now
                                        String endDate = complaintEndDate.get(complaintUsername.indexOf(userNameToCheck));
                                        Calendar calendar = Calendar.getInstance();
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
                                        try {
                                            Date d1 = sdf.parse(endDate);
                                            String currentDate = sdf.format(calendar.getTime());
                                            Date d2 = sdf.parse(currentDate);

                                            if (d1.after(d2)) {
                                                // This means that the end date still has not arrived, no logging in!
                                                Log.d("BAN", "You are banned until " + endDate);
                                                Intent Login = new Intent(GeneralLogin.this, Welcomeback_chef.class );
                                                Login.putExtra("banned", endDate);

                                                startActivity(Login);
                                                username.setText("");
                                                password.setText("");
                                                loginfound = true;
                                            } else {
                                                Intent Login = new Intent(GeneralLogin.this, Welcomeback_chef.class);
                                                Login.putExtra("username",userNameToCheck);
                                                startActivity(Login);
                                                username.setText("");
                                                password.setText("");
                                                loginfound = true;
                                            }

                                        } catch (Exception e){
                                            Log.d("BAN", "You are banned indefinitely");
                                            Intent Login = new Intent(GeneralLogin.this, Welcomeback_chef.class);
                                            Login.putExtra("banned", "indefinitely");
                                            startActivity(Login);
                                            username.setText("");
                                            password.setText("");
                                            loginfound = true;
                                        }


                                    } else {
                                        // This means the complaint has not been addressed and thus the chef is not banned
                                        Intent Login = new Intent(GeneralLogin.this, Welcomeback_chef.class);
                                        Login.putExtra("username", username.getText().toString());
                                        startActivity(Login);
                                        username.setText("");
                                        password.setText("");
                                        loginfound = true;
                                    }
                                } else {
                                    // If there has been no complaints, the chef can log in just fine
                                    Intent Login = new Intent(GeneralLogin.this, Welcomeback_chef.class);
                                    Login.putExtra("username", username.getText().toString());
                                    startActivity(Login);
                                    username.setText("");
                                    password.setText("");
                                    loginfound = true;
                                }


                            } else {
                                Snackbar incorrect = Snackbar.make(view, "Incorrect Username or Password", Snackbar.LENGTH_LONG);
                                incorrect.show();
                            }
                        }
                    }

                    // Display snackbar for incorrect password, PUT THIS STATEMENT IN AN ELSE
                    if (!loginfound) {
                        Snackbar incorrect = Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), "Incorrect username or password or account doesn't exist", Snackbar.LENGTH_LONG);
                        incorrect.show();
                        //Toast.makeText(getApplicationContext(), "Incorrect username or password", Toast.LENGTH_LONG).show();
                    }
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

        complaintReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                complaintUsername.clear();
                complaintEndDate.clear();
                complaintAddressed.clear();
                for (DataSnapshot complaintShot : snapshot.getChildren()){
                    String username = complaintShot.child("chefUsername").getValue().toString();
                    String endDate = complaintShot.child("endDate").getValue().toString();
                    boolean isAssessed = (Boolean) complaintShot.child("addressed").getValue();

                    complaintUsername.add(username);
                    complaintEndDate.add(endDate);
                    complaintAddressed.add(isAssessed);
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