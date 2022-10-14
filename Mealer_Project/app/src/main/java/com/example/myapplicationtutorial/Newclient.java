package com.example.myapplicationtutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Newclient extends AppCompatActivity {

    DatabaseReference databaseUser;

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

                pushToFirebase();
            }
        });

    }
    private void pushToFirebase(){
        //creating instance of database
        databaseUser = FirebaseDatabase.getInstance().getReference("Client");

        //get user input
        EditText clientUsername = (EditText)findViewById(R.id.username2);
        String username = clientUsername.getText().toString().trim();

        //get available key
        String id = databaseUser.push().getKey();


        Client client = new Client(id,username);

        databaseUser.child(client.getId()).setValue(username);
    };

    protected void onStart() {
        super.onStart();
        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Client client = postSnapshot.getValue(Client.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}