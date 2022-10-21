package com.example.myapplicationtutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class NewClient extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private DatabaseReference chefInfo;
    private DatabaseReference clientInfo;
    private DatabaseReference adminInfo;

    private String username;
    private String password;
    private String fullname;
    List<String> usernames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        //creating database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Client");
        chefInfo = FirebaseDatabase.getInstance().getReference("Chef");
        clientInfo = FirebaseDatabase.getInstance().getReference("Client");
        adminInfo = FirebaseDatabase.getInstance().getReference("Admin");

        usernames = new ArrayList<String>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newclient);

        Button loginButton = (Button)findViewById(R.id.login2);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText fullnamenewclient = (EditText) findViewById(R.id.fullnamenewclient);
                EditText usernamenewclient = (EditText) findViewById(R.id.usernamenewclient);
                EditText passwordnewclient = (EditText) findViewById(R.id.passwordnewclient);
                EditText confirmpasswordnewclient = (EditText) findViewById(R.id.confirmpasswordnewclient);
                if (fullnamenewclient.getText().toString().trim().isEmpty() || usernamenewclient.getText().toString().trim().isEmpty() ||
                        passwordnewclient.getText().toString().trim().isEmpty() || confirmpasswordnewclient.getText().toString().trim().isEmpty()) {
                    Log.d("TAG","Username or Password cannot be empty");
                    //Toast.makeText(GeneralLogin.this, "Username or Password cannot be empty", Toast.LENGTH_LONG).show();
                } else if (passwordnewclient.getText().toString().equals(confirmpasswordnewclient.getText().toString()) == false){
                    Snackbar passwordsDontMatch = Snackbar.make(view, "Passwords do not match", Snackbar.LENGTH_LONG);
                    passwordsDontMatch.show();

                }else{
                    username = usernamenewclient.getText().toString().trim();
                    password = passwordnewclient.getText().toString().trim();
                    fullname = fullnamenewclient.getText().toString().trim();
                    boolean addData = true;
                    for (int i = 0; i < usernames.size(); i++){
                        if (username.equals(usernames.get(i))){
                            Snackbar usernameInUse = Snackbar.make(view, "Username is already in use", Snackbar.LENGTH_LONG);
                            usernameInUse.show();
                            // Make sure that the user does not get to sign up with a duplicate username
                            addData = false;
                        }
                    }

                    if (addData) {
                        uploadData();
                        finish();
                    }
                }

            }
        });
    }
    protected void uploadData(){
        //Assumes chef attributes are correct

        Client newClient = new Client(databaseReference.push().getKey(),username,password,fullname);

        databaseReference.child(newClient.getId()).setValue(newClient);
    }

    protected void onStart(){
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usernames.clear();
                chefInfo.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot chef) {

                        for (DataSnapshot chefShot : chef.getChildren()){
                            // Copies two of them for some reason
                            String test = chefShot.child("username").getValue().toString();
                            usernames.add(test);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                clientInfo.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot client) {
                        for (DataSnapshot clientShot : client.getChildren()){
                            // Copies two of them for some reason
                            String test = clientShot.child("username").getValue().toString();

                            usernames.add(test);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                adminInfo.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot admin) {
                        for (DataSnapshot adminShot : admin.getChildren()){
                            // Copies two of them for some reason
                            String test = adminShot.child("username").getValue().toString();
                            usernames.add(test);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}