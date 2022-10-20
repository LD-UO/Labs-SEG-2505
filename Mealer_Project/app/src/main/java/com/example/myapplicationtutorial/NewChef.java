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


public class NewChef extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private String username;
    private String password;
    private String fullname;
    List<String> usernames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //creating database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Chef");
        usernames = new ArrayList<String>();

        setContentView(R.layout.activity_newchef);

        Button loginButton = (Button)findViewById(R.id.login);
        onStart();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText fullnamenewchef = (EditText) findViewById(R.id.fullnamenewclient);
                EditText usernamenewchef = (EditText) findViewById(R.id.usernamenewclient);
                EditText passwordnewchef = (EditText) findViewById(R.id.passwordnewclient);
                EditText confirmpasswordnewchef = (EditText) findViewById(R.id.confirmpasswordnewclient);
                if (fullnamenewchef.getText().toString().trim().isEmpty() || usernamenewchef.getText().toString().trim().isEmpty() ||
                        passwordnewchef.getText().toString().trim().isEmpty() || confirmpasswordnewchef.getText().toString().trim().isEmpty()) {
                    Log.d("TAG","Username or Password cannot be empty");
                    //Toast.makeText(GeneralLogin.this, "Username or Password cannot be empty", Toast.LENGTH_LONG).show();
                } else{
                    username = usernamenewchef.getText().toString().trim();
                    password = passwordnewchef.getText().toString().trim();
                    fullname = fullnamenewchef.getText().toString().trim();
                    boolean addData = true;
                    for (int i = 0; i < usernames.size() / 2; i++){
                        if (username.equals(usernames.get(i))){
                            Snackbar test = Snackbar.make(view, "Username is already in use", Snackbar.LENGTH_LONG);
                            test.show();
                            addData = false;
                        }
                    }

                    if (addData) {
                        uploadData();
                        finish();
                    }
                    // "The code below will need to be implemented correctly"
                    //Intent intent = new Intent(GeneralLogin.this, WelcomebackAdminActivity.class);
                    // Will need to change the text here to reflect database access!
                    //intent.putExtra("userType", "Undecided");
                    //startActivity(intent);
                }
            }
        });
    }
    protected void uploadData(){
        //Assumes chef attributes are correct

        Chef newChef = new Chef(databaseReference.push().getKey(),username,password,fullname);

        databaseReference.child(newChef.getId()).setValue(newChef);
    }

    protected void onStart(){
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usernames.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    // Copies two of them for some reason
                    String test = postSnapshot.child("username").getValue().toString();
                    Log.d("TEST", test);
                    usernames.add(test);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}