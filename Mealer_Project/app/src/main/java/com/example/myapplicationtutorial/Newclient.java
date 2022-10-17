package com.example.myapplicationtutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewClient extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private String username;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //creating database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Client");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newclient);

        Button loginButton = (Button)findViewById(R.id.login2);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewClient.this, Welcomeback.class);
                intent.putExtra("userType", "Client");
                startActivity(intent);
                uploadData();
            }
        });
    }
    protected void uploadData(){
        //Assumes chef attributes are correct

        Client newClient = new Client(databaseReference.push().getKey(),username,password);

        databaseReference.child(newClient.getId()).setValue(newClient);
    }
}