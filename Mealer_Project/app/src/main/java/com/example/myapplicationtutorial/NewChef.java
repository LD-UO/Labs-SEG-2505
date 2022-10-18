package com.example.myapplicationtutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
                Intent intent = new Intent(NewChef.this, Welcomeback.class);
                intent.putExtra("userType", "Chef");
                startActivity(intent);
                uploadData();
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