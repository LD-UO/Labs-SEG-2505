package com.example.myapplicationtutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Newclient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newclient);


        Button loginButton = (Button)findViewById(R.id.login2);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Newclient.this, Welcomeback.class);
                intent.putExtra("userType", "Client");
                startActivity(intent);

                //creating instance of database
                DatabaseReference databaseUser;
                databaseUser = FirebaseDatabase.getInstance().getReference("user");
                EditText clientUsername = (EditText)findViewById(R.id.username2);
                String username = clientUsername.getText().toString().trim();
                String id = databaseUser.push().getKey();
                Client client = new Client(id,username);
                databaseUser.child(client.getId()).setValue(username);
            }
        });
    }
}