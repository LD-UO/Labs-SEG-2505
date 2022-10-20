package com.example.myapplicationtutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NewClient extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private String username;
    private String password;
    private String fullname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        //creating database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Client");

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
                } else{
                    username = usernamenewclient.getText().toString().trim();
                    password = passwordnewclient.getText().toString().trim();
                    fullname = fullnamenewclient.getText().toString().trim();
                    uploadData();
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

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}