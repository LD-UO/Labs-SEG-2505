package com.example.myapplicationtutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.*;

public class NewChef extends AppCompatActivity {


    private DatabaseReference databaseReference;
    private String username="test";
    private String password="22";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        getSupportActionBar().hide();
        //creating database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Chef");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newchef);

        Button loginButton = (Button)findViewById(R.id.login);

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

        Chef newChef = new Chef(databaseReference.push().getKey(),username,password);

        databaseReference.child(newChef.getId()).setValue(newChef);
    }

    protected void onStart(){
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
            }

            public void onCancelled(DatabaseError error) {
            }
        });
        }

}